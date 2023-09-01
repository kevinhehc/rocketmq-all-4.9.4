/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.store;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.apache.rocketmq.store.util.LibC;
import sun.nio.ch.DirectBuffer;

public class TransientStorePool {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.STORE_LOGGER_NAME);

    //池中预分配ByteBuffer数量
    private final int poolSize;
    //每个ByteBuffer大小
    private final int fileSize;
    //采用双端队列维护预分配的ByteBuffer
    private final Deque<ByteBuffer> availableBuffers;
    private final MessageStoreConfig storeConfig;

    public TransientStorePool(final MessageStoreConfig storeConfig) {
        this.storeConfig = storeConfig;
        this.poolSize = storeConfig.getTransientStorePoolSize();
        this.fileSize = storeConfig.getMappedFileSizeCommitLog();
        this.availableBuffers = new ConcurrentLinkedDeque<>();
    }

    /**
     * It's a heavy init method.
     */
    //如源码注释，因为这里需要申请多个堆外ByteBuffer，所以是个
    //十分heavy的初始化方法
    public void init() {
        //申请poolSize个ByteBuffer
        for (int i = 0; i < poolSize; i++) {
            //申请直接内存空间
            //分配堆外内存，默认大小1G
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(fileSize);

            final long address = ((DirectBuffer) byteBuffer).address();
            Pointer pointer = new Pointer(address);

            //锁住内存，避免操作系统虚拟内存的换入换出
            //锁定堆外内存，确保不会置换到虚拟内存中去
            LibC.INSTANCE.mlock(pointer, new NativeLong(fileSize));

            //将预分配的ByteBuffer方法队列中
            availableBuffers.offer(byteBuffer);
        }
    }

    //销毁内存池
    public void destroy() {
        //取消对内存的锁定
        for (ByteBuffer byteBuffer : availableBuffers) {
            final long address = ((DirectBuffer) byteBuffer).address();
            Pointer pointer = new Pointer(address);
            LibC.INSTANCE.munlock(pointer, new NativeLong(fileSize));
        }
    }

    //使用完毕之后归还ByteBuffer
    public void returnBuffer(ByteBuffer byteBuffer) {
        //ByteBuffer各下标复位
        byteBuffer.position(0);
        byteBuffer.limit(fileSize);
        //放入队头，等待下次重新被分配
        this.availableBuffers.offerFirst(byteBuffer);
    }

    //从池中获取ByteBuffer
    public ByteBuffer borrowBuffer() {
        //非阻塞弹出队头元素，如果没有启用暂存池，则
        //不会调用init方法，队列中就没有元素，这里返回null
        //其次，如果队列中所有元素都被借用出去，队列也为空
        //此时也会返回null
        ByteBuffer buffer = availableBuffers.pollFirst();

        //如果队列中剩余元素数量小于配置个数的0.4，则写日志提示
        if (availableBuffers.size() < poolSize * 0.4) {
            log.warn("TransientStorePool only remain {} sheets.", availableBuffers.size());
        }
        return buffer;
    }

    //剩下可借出的ByteBuffer数量
    public int availableBufferNums() {
        //如果启动，则返回可用的堆外内存此的数量
        if (storeConfig.isTransientStorePoolEnable()) {
            return availableBuffers.size();
        }
        //否则返会Integer.MAX_VALUE
        return Integer.MAX_VALUE;
    }
}
