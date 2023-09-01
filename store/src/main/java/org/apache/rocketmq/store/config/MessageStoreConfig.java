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
package org.apache.rocketmq.store.config;

import java.io.File;

import org.apache.rocketmq.common.annotation.ImportantField;
import org.apache.rocketmq.store.ConsumeQueue;

public class MessageStoreConfig {

    public static final String MULTI_PATH_SPLITTER = System.getProperty("rocketmq.broker.multiPathSplitter", ",");

    //The root directory in which the log data is kept
    @ImportantField
    // 文件存储的根路径
    private String storePathRootDir = System.getProperty("user.home") + File.separator + "store";

    //The directory in which the commitlog is kept
    @ImportantField
    // commitLog的存储路径
    private String storePathCommitLog = null;

    @ImportantField
    // DLedgerCommitLog的存储路径
    private String storePathDLedgerCommitLog = null;

    // 只读的commitLog的路径
    private String readOnlyCommitLogStorePaths = null;

    // CommitLog file size,default is 1G
    // commitLog 文件大小，默认1G
    private int mappedFileSizeCommitLog = 1024 * 1024 * 1024;
    // 消费队列文件大小，默认30W
    // ConsumeQueue file size,default is 30W
    private int mappedFileSizeConsumeQueue = 300000 * ConsumeQueue.CQ_STORE_UNIT_SIZE;
    // enable consume queue ext
    // ？？？
    private boolean enableConsumeQueueExt = false;
    // ConsumeQueue extend file size, 48M
    // 消费队列扩展文件大小，48M
    private int mappedFileSizeConsumeQueueExt = 48 * 1024 * 1024;
    // Bit count of filter bit map.
    // 过滤器位图的位数。
    // this will be set by pipe of calculate filter bit map.
    // 这将由计算过滤器位图的管道设置。
    private int bitMapLengthConsumeQueueExt = 64;

    // CommitLog flush interval
    // CommitLog 刷新间隔
    // flush data to disk
    // 将数据刷新到磁盘
    @ImportantField
    private int flushIntervalCommitLog = 500;

    // Only used if TransientStorePool enabled
    // 仅在启用 TransientStorePool 时使用
    // flush data to FileChannel
    // 将数据刷新到 FileChannel
    // commitLog提交频率
    @ImportantField
    private int commitIntervalCommitLog = 200;

    /**
     * introduced since 4.0.x. Determine whether to use mutex reentrantLock when putting message.<br/>
     */
    private boolean useReentrantLockWhenPutMessage = true;

    // Whether schedule flush
    @ImportantField
    // 是否周期刷盘
    private boolean flushCommitLogTimed = true;
    // ConsumeQueue flush interval
    // 消费队列刷盘周期
    private int flushIntervalConsumeQueue = 1000;
    // Resource reclaim interval
    // 资源回收间隔
    private int cleanResourceInterval = 10000;
    // CommitLog removal interval
    // CommitLog 删除间隔
    private int deleteCommitLogFilesInterval = 100;
    // ConsumeQueue removal interval
    // ConsumeQueue 移除间隔
    private int deleteConsumeQueueFilesInterval = 100;
    private int destroyMapedFileIntervalForcibly = 1000 * 120;
    private int redeleteHangedFileInterval = 1000 * 120;
    // When to delete,default is at 4 am
    @ImportantField
    // 凌晨4点删除文件
    private String deleteWhen = "04";
    //磁盘最大的使用比例 75%
    private int diskMaxUsedSpaceRatio = 75;
    // The number of hours to keep a log file before deleting it (in hours)
    @ImportantField
    // 文件保留的时间，72小时=3天
    private int fileReservedTime = 72;
    // Flow control for ConsumeQueue
    // 存储消息的高水位，消费队列的流量控制
    private int putMsgIndexHightWater = 600000;
    // The maximum size of message body,default is 4M,4M only for body length,not include others.
    // 消息体的最大值4M，只是消息体大小，不包括其他的
    private int maxMessageSize = 1024 * 1024 * 4;
    // Whether check the CRC32 of the records consumed.
    // 是否校验消费记录的CRC32。
    // This ensures no on-the-wire or on-disk corruption to the messages occurred.
    // 这确保不会发生消息的在线或磁盘损坏。
    // This check adds some overhead,so it may be disabled in cases seeking extreme performance.
    // 此检查会增加一些开销，因此在寻求极端性能的情况下可能会禁用它。
    private boolean checkCRCOnRecover = true;
    // How many pages are to be flushed when flush CommitLog
    // flush CommitLog时要flush多少页
    private int flushCommitLogLeastPages = 4;
    // How many pages are to be committed when commit data to file
    // 将数据提交到文件时要提交多少页
    private int commitCommitLogLeastPages = 4;
    // Flush page size when the disk in warming state
    // 磁盘处于预热状态时刷新页面大小
    private int flushLeastPagesWhenWarmMapedFile = 1024 / 4 * 16;
    // How many pages are to be flushed when flush ConsumeQueue
    // 一次刷盘至少需要脏页的数量,默认2页,针对 Consume文件
    private int flushConsumeQueueLeastPages = 2;
    // commitlog两次刷盘的最大间隔,如果超过该间隔,将fushCommitLogLeastPages要求直接执行刷盘操作
    private int flushCommitLogThoroughInterval = 1000 * 10;
    // Commitlog两次提交的最大间隔,如果超过该间隔,将忽略commitCommitLogLeastPages直接提交
    private int commitCommitLogThoroughInterval = 200;
    //Consume两次刷盘的最大间隔,如果超过该间隔,将忽略
    private int flushConsumeQueueThoroughInterval = 1000 * 60;
    @ImportantField
    // 一次服务端消息拉取,消息在内存中传输允许的最大传输字节数默认256kb
    private int maxTransferBytesOnMessageInMemory = 1024 * 256;
    @ImportantField
    // 一次服务消息拉取,消息在内存中传输运行的最大消息条数,默认为32条
    private int maxTransferCountOnMessageInMemory = 32;
    @ImportantField
    //一次服务消息端消息拉取,消息在磁盘中传输允许的最大字节
    private int maxTransferBytesOnMessageInDisk = 1024 * 64;
    @ImportantField
    // 一次消息服务端消息拉取,消息在磁盘中传输允许的最大条数,默认为8条
    private int maxTransferCountOnMessageInDisk = 8;
    @ImportantField
    // 访问消息在内存中比率,默认为40%
    private int accessMessageInMemoryMaxRatio = 40;
    @ImportantField
    // 是否支持消息索引文件
    private boolean messageIndexEnable = true;
    // 单个索引文件hash槽的个数,默认为五百万
    private int maxHashSlotNum = 5000000;
    // 单个索引文件索引条目的个数,默认为两千万
    private int maxIndexNum = 5000000 * 4;
    // 一次查询消息最大返回消息条数,默认64条
    private int maxMsgsNumBatch = 64;
    @ImportantField
    // 消息索引是否安全,默认为 false,文件恢复时选择文件检测点（commitlog.consumeque）的最小的与文件最后更新对比，
    // 如果为true，文件恢复时选择文件检测点保存的索引更新时间作为对比
    private boolean messageIndexSafe = false;
    // Master监听端口,从服务器连接该端口,默认为10912
    private int haListenPort = 10912;
    // Master与Slave心跳包发送间隔
    private int haSendHeartbeatInterval = 1000 * 5;
    // Master与save长连接空闲时间,超过该时间将关闭连接
    private int haHousekeepingInterval = 1000 * 20;
    // 一次HA主从同步传输的最大字节长度,默认为32K
    private int haTransferBatchSize = 1024 * 32;
    @ImportantField
    // Master服务器IP地址与端口号
    private String haMasterAddress = null;
    // 允许从服务器落户的最大偏移字节数,默认为256M。超过该值则表示该Slave不可用
    private int haSlaveFallbehindMax = 1024 * 1024 * 256;
    @ImportantField
    //角色：默认是异步的Master
    private BrokerRole brokerRole = BrokerRole.ASYNC_MASTER;
    @ImportantField
    // 刷盘方式，默认异步
    private FlushDiskType flushDiskType = FlushDiskType.ASYNC_FLUSH;
    // 刷盘超时时间 5秒
    private int syncFlushTimeout = 1000 * 5;
    // 从节点超时时间 3秒
    private int slaveTimeout = 3000;
    // 消息衰减的时间
    private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
    // 延迟队列拉取进度刷盘间隔。默认10s
    private long flushDelayOffsetInterval = 1000 * 10;
    @ImportantField
    //是否支持强行删除过期文件
    private boolean cleanFileForciblyEnable = true;
    // 是否温和地使用 MappedFile如果为true,将不强制将内存映射文件锁定在内存中
    private boolean warmMapedFileEnable = false;
    // 从服务器是否坚持 offset检测
    private boolean offsetCheckInSlave = false;
    // 是否支持 PutMessage Lock锁打印信息
    private boolean debugLockEnable = false;
    //是否允许重复复制,默认为 false
    private boolean duplicationEnable = false;
    // 是否统计磁盘的使用情况
    private boolean diskFallRecorded = true;
    // putMessage锁占用超过该时间,表示 PageCache忙
    private long osPageCacheBusyTimeOutMills = 1000;
    // 查询消息默认返回条数,默认为32
    private int defaultQueryMaxNum = 32;

    @ImportantField
    // Commitlog是否开启 transientStorePool机制,默认为 false
    private boolean transientStorePoolEnable = false;
    //transientStorePool中缓存 ByteBuffer个数,默认5个
    private int transientStorePoolSize = 5;
    //从 transientStorepool中获取 ByteBuffer是否支持快速失败
    private boolean fastFailIfNoBufferInStorePool = false;

    private boolean enableDLegerCommitLog = false;
    private String dLegerGroup;
    private String dLegerPeers;
    private String dLegerSelfId;

    private String preferredLeaderId;

    private boolean isEnableBatchPush = false;

    private boolean enableScheduleMessageStats = true;

    private boolean enableLmq = false;
    private boolean enableMultiDispatch = false;
    private int maxLmqConsumeQueueNum = 20000;

    private boolean enableScheduleAsyncDeliver = false;
    private int scheduleAsyncDeliverMaxPendingLimit = 2000;
    private int scheduleAsyncDeliverMaxResendNum2Blocked = 3;

    public boolean isDebugLockEnable() {
        return debugLockEnable;
    }

    public void setDebugLockEnable(final boolean debugLockEnable) {
        this.debugLockEnable = debugLockEnable;
    }

    public boolean isDuplicationEnable() {
        return duplicationEnable;
    }

    public void setDuplicationEnable(final boolean duplicationEnable) {
        this.duplicationEnable = duplicationEnable;
    }

    public long getOsPageCacheBusyTimeOutMills() {
        return osPageCacheBusyTimeOutMills;
    }

    public void setOsPageCacheBusyTimeOutMills(final long osPageCacheBusyTimeOutMills) {
        this.osPageCacheBusyTimeOutMills = osPageCacheBusyTimeOutMills;
    }

    public boolean isDiskFallRecorded() {
        return diskFallRecorded;
    }

    public void setDiskFallRecorded(final boolean diskFallRecorded) {
        this.diskFallRecorded = diskFallRecorded;
    }

    public boolean isWarmMapedFileEnable() {
        return warmMapedFileEnable;
    }

    public void setWarmMapedFileEnable(boolean warmMapedFileEnable) {
        this.warmMapedFileEnable = warmMapedFileEnable;
    }

    public int getMappedFileSizeCommitLog() {
        return mappedFileSizeCommitLog;
    }

    public void setMappedFileSizeCommitLog(int mappedFileSizeCommitLog) {
        this.mappedFileSizeCommitLog = mappedFileSizeCommitLog;
    }

    public int getMappedFileSizeConsumeQueue() {

        int factor = (int) Math.ceil(this.mappedFileSizeConsumeQueue / (ConsumeQueue.CQ_STORE_UNIT_SIZE * 1.0));
        return (int) (factor * ConsumeQueue.CQ_STORE_UNIT_SIZE);
    }

    public void setMappedFileSizeConsumeQueue(int mappedFileSizeConsumeQueue) {
        this.mappedFileSizeConsumeQueue = mappedFileSizeConsumeQueue;
    }

    public boolean isEnableConsumeQueueExt() {
        return enableConsumeQueueExt;
    }

    public void setEnableConsumeQueueExt(boolean enableConsumeQueueExt) {
        this.enableConsumeQueueExt = enableConsumeQueueExt;
    }

    public int getMappedFileSizeConsumeQueueExt() {
        return mappedFileSizeConsumeQueueExt;
    }

    public void setMappedFileSizeConsumeQueueExt(int mappedFileSizeConsumeQueueExt) {
        this.mappedFileSizeConsumeQueueExt = mappedFileSizeConsumeQueueExt;
    }

    public int getBitMapLengthConsumeQueueExt() {
        return bitMapLengthConsumeQueueExt;
    }

    public void setBitMapLengthConsumeQueueExt(int bitMapLengthConsumeQueueExt) {
        this.bitMapLengthConsumeQueueExt = bitMapLengthConsumeQueueExt;
    }

    public int getFlushIntervalCommitLog() {
        return flushIntervalCommitLog;
    }

    public void setFlushIntervalCommitLog(int flushIntervalCommitLog) {
        this.flushIntervalCommitLog = flushIntervalCommitLog;
    }

    public int getFlushIntervalConsumeQueue() {
        return flushIntervalConsumeQueue;
    }

    public void setFlushIntervalConsumeQueue(int flushIntervalConsumeQueue) {
        this.flushIntervalConsumeQueue = flushIntervalConsumeQueue;
    }

    public int getPutMsgIndexHightWater() {
        return putMsgIndexHightWater;
    }

    public void setPutMsgIndexHightWater(int putMsgIndexHightWater) {
        this.putMsgIndexHightWater = putMsgIndexHightWater;
    }

    public int getCleanResourceInterval() {
        return cleanResourceInterval;
    }

    public void setCleanResourceInterval(int cleanResourceInterval) {
        this.cleanResourceInterval = cleanResourceInterval;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public boolean isCheckCRCOnRecover() {
        return checkCRCOnRecover;
    }

    public boolean getCheckCRCOnRecover() {
        return checkCRCOnRecover;
    }

    public void setCheckCRCOnRecover(boolean checkCRCOnRecover) {
        this.checkCRCOnRecover = checkCRCOnRecover;
    }

    public String getStorePathCommitLog() {
        if (storePathCommitLog == null) {
            return storePathRootDir + File.separator + "commitlog";
        }
        return storePathCommitLog;
    }

    public void setStorePathCommitLog(String storePathCommitLog) {
        this.storePathCommitLog = storePathCommitLog;
    }

    public String getStorePathDLedgerCommitLog() {
        return storePathDLedgerCommitLog;
    }

    public void setStorePathDLedgerCommitLog(String storePathDLedgerCommitLog) {
        this.storePathDLedgerCommitLog = storePathDLedgerCommitLog;
    }

    public String getDeleteWhen() {
        return deleteWhen;
    }

    public void setDeleteWhen(String deleteWhen) {
        this.deleteWhen = deleteWhen;
    }

    public int getDiskMaxUsedSpaceRatio() {
        if (this.diskMaxUsedSpaceRatio < 10)
            return 10;

        if (this.diskMaxUsedSpaceRatio > 95)
            return 95;

        return diskMaxUsedSpaceRatio;
    }

    public void setDiskMaxUsedSpaceRatio(int diskMaxUsedSpaceRatio) {
        this.diskMaxUsedSpaceRatio = diskMaxUsedSpaceRatio;
    }

    public int getDeleteCommitLogFilesInterval() {
        return deleteCommitLogFilesInterval;
    }

    public void setDeleteCommitLogFilesInterval(int deleteCommitLogFilesInterval) {
        this.deleteCommitLogFilesInterval = deleteCommitLogFilesInterval;
    }

    public int getDeleteConsumeQueueFilesInterval() {
        return deleteConsumeQueueFilesInterval;
    }

    public void setDeleteConsumeQueueFilesInterval(int deleteConsumeQueueFilesInterval) {
        this.deleteConsumeQueueFilesInterval = deleteConsumeQueueFilesInterval;
    }

    public int getMaxTransferBytesOnMessageInMemory() {
        return maxTransferBytesOnMessageInMemory;
    }

    public void setMaxTransferBytesOnMessageInMemory(int maxTransferBytesOnMessageInMemory) {
        this.maxTransferBytesOnMessageInMemory = maxTransferBytesOnMessageInMemory;
    }

    public int getMaxTransferCountOnMessageInMemory() {
        return maxTransferCountOnMessageInMemory;
    }

    public void setMaxTransferCountOnMessageInMemory(int maxTransferCountOnMessageInMemory) {
        this.maxTransferCountOnMessageInMemory = maxTransferCountOnMessageInMemory;
    }

    public int getMaxTransferBytesOnMessageInDisk() {
        return maxTransferBytesOnMessageInDisk;
    }

    public void setMaxTransferBytesOnMessageInDisk(int maxTransferBytesOnMessageInDisk) {
        this.maxTransferBytesOnMessageInDisk = maxTransferBytesOnMessageInDisk;
    }

    public int getMaxTransferCountOnMessageInDisk() {
        return maxTransferCountOnMessageInDisk;
    }

    public void setMaxTransferCountOnMessageInDisk(int maxTransferCountOnMessageInDisk) {
        this.maxTransferCountOnMessageInDisk = maxTransferCountOnMessageInDisk;
    }

    public int getFlushCommitLogLeastPages() {
        return flushCommitLogLeastPages;
    }

    public void setFlushCommitLogLeastPages(int flushCommitLogLeastPages) {
        this.flushCommitLogLeastPages = flushCommitLogLeastPages;
    }

    public int getFlushConsumeQueueLeastPages() {
        return flushConsumeQueueLeastPages;
    }

    public void setFlushConsumeQueueLeastPages(int flushConsumeQueueLeastPages) {
        this.flushConsumeQueueLeastPages = flushConsumeQueueLeastPages;
    }

    public int getFlushCommitLogThoroughInterval() {
        return flushCommitLogThoroughInterval;
    }

    public void setFlushCommitLogThoroughInterval(int flushCommitLogThoroughInterval) {
        this.flushCommitLogThoroughInterval = flushCommitLogThoroughInterval;
    }

    public int getFlushConsumeQueueThoroughInterval() {
        return flushConsumeQueueThoroughInterval;
    }

    public void setFlushConsumeQueueThoroughInterval(int flushConsumeQueueThoroughInterval) {
        this.flushConsumeQueueThoroughInterval = flushConsumeQueueThoroughInterval;
    }

    public int getDestroyMapedFileIntervalForcibly() {
        return destroyMapedFileIntervalForcibly;
    }

    public void setDestroyMapedFileIntervalForcibly(int destroyMapedFileIntervalForcibly) {
        this.destroyMapedFileIntervalForcibly = destroyMapedFileIntervalForcibly;
    }

    public int getFileReservedTime() {
        return fileReservedTime;
    }

    public void setFileReservedTime(int fileReservedTime) {
        this.fileReservedTime = fileReservedTime;
    }

    public int getRedeleteHangedFileInterval() {
        return redeleteHangedFileInterval;
    }

    public void setRedeleteHangedFileInterval(int redeleteHangedFileInterval) {
        this.redeleteHangedFileInterval = redeleteHangedFileInterval;
    }

    public int getAccessMessageInMemoryMaxRatio() {
        return accessMessageInMemoryMaxRatio;
    }

    public void setAccessMessageInMemoryMaxRatio(int accessMessageInMemoryMaxRatio) {
        this.accessMessageInMemoryMaxRatio = accessMessageInMemoryMaxRatio;
    }

    public boolean isMessageIndexEnable() {
        return messageIndexEnable;
    }

    public void setMessageIndexEnable(boolean messageIndexEnable) {
        this.messageIndexEnable = messageIndexEnable;
    }

    public int getMaxHashSlotNum() {
        return maxHashSlotNum;
    }

    public void setMaxHashSlotNum(int maxHashSlotNum) {
        this.maxHashSlotNum = maxHashSlotNum;
    }

    public int getMaxIndexNum() {
        return maxIndexNum;
    }

    public void setMaxIndexNum(int maxIndexNum) {
        this.maxIndexNum = maxIndexNum;
    }

    public int getMaxMsgsNumBatch() {
        return maxMsgsNumBatch;
    }

    public void setMaxMsgsNumBatch(int maxMsgsNumBatch) {
        this.maxMsgsNumBatch = maxMsgsNumBatch;
    }

    public int getHaListenPort() {
        return haListenPort;
    }

    public void setHaListenPort(int haListenPort) {
        this.haListenPort = haListenPort;
    }

    public int getHaSendHeartbeatInterval() {
        return haSendHeartbeatInterval;
    }

    public void setHaSendHeartbeatInterval(int haSendHeartbeatInterval) {
        this.haSendHeartbeatInterval = haSendHeartbeatInterval;
    }

    public int getHaHousekeepingInterval() {
        return haHousekeepingInterval;
    }

    public void setHaHousekeepingInterval(int haHousekeepingInterval) {
        this.haHousekeepingInterval = haHousekeepingInterval;
    }

    public BrokerRole getBrokerRole() {
        return brokerRole;
    }

    public void setBrokerRole(BrokerRole brokerRole) {
        this.brokerRole = brokerRole;
    }

    public void setBrokerRole(String brokerRole) {
        this.brokerRole = BrokerRole.valueOf(brokerRole);
    }

    public int getHaTransferBatchSize() {
        return haTransferBatchSize;
    }

    public void setHaTransferBatchSize(int haTransferBatchSize) {
        this.haTransferBatchSize = haTransferBatchSize;
    }

    public int getHaSlaveFallbehindMax() {
        return haSlaveFallbehindMax;
    }

    public void setHaSlaveFallbehindMax(int haSlaveFallbehindMax) {
        this.haSlaveFallbehindMax = haSlaveFallbehindMax;
    }

    public FlushDiskType getFlushDiskType() {
        return flushDiskType;
    }

    public void setFlushDiskType(FlushDiskType flushDiskType) {
        this.flushDiskType = flushDiskType;
    }

    public void setFlushDiskType(String type) {
        this.flushDiskType = FlushDiskType.valueOf(type);
    }

    public int getSyncFlushTimeout() {
        return syncFlushTimeout;
    }

    public void setSyncFlushTimeout(int syncFlushTimeout) {
        this.syncFlushTimeout = syncFlushTimeout;
    }

    public int getSlaveTimeout() {
        return slaveTimeout;
    }

    public void setSlaveTimeout(int slaveTimeout) {
        this.slaveTimeout = slaveTimeout;
    }

    public String getHaMasterAddress() {
        return haMasterAddress;
    }

    public void setHaMasterAddress(String haMasterAddress) {
        this.haMasterAddress = haMasterAddress;
    }

    public String getMessageDelayLevel() {
        return messageDelayLevel;
    }

    public void setMessageDelayLevel(String messageDelayLevel) {
        this.messageDelayLevel = messageDelayLevel;
    }

    public long getFlushDelayOffsetInterval() {
        return flushDelayOffsetInterval;
    }

    public void setFlushDelayOffsetInterval(long flushDelayOffsetInterval) {
        this.flushDelayOffsetInterval = flushDelayOffsetInterval;
    }

    public boolean isCleanFileForciblyEnable() {
        return cleanFileForciblyEnable;
    }

    public void setCleanFileForciblyEnable(boolean cleanFileForciblyEnable) {
        this.cleanFileForciblyEnable = cleanFileForciblyEnable;
    }

    public boolean isMessageIndexSafe() {
        return messageIndexSafe;
    }

    public void setMessageIndexSafe(boolean messageIndexSafe) {
        this.messageIndexSafe = messageIndexSafe;
    }

    public boolean isFlushCommitLogTimed() {
        return flushCommitLogTimed;
    }

    public void setFlushCommitLogTimed(boolean flushCommitLogTimed) {
        this.flushCommitLogTimed = flushCommitLogTimed;
    }

    public String getStorePathRootDir() {
        return storePathRootDir;
    }

    public void setStorePathRootDir(String storePathRootDir) {
        this.storePathRootDir = storePathRootDir;
    }

    public int getFlushLeastPagesWhenWarmMapedFile() {
        return flushLeastPagesWhenWarmMapedFile;
    }

    public void setFlushLeastPagesWhenWarmMapedFile(int flushLeastPagesWhenWarmMapedFile) {
        this.flushLeastPagesWhenWarmMapedFile = flushLeastPagesWhenWarmMapedFile;
    }

    public boolean isOffsetCheckInSlave() {
        return offsetCheckInSlave;
    }

    public void setOffsetCheckInSlave(boolean offsetCheckInSlave) {
        this.offsetCheckInSlave = offsetCheckInSlave;
    }

    public int getDefaultQueryMaxNum() {
        return defaultQueryMaxNum;
    }

    public void setDefaultQueryMaxNum(int defaultQueryMaxNum) {
        this.defaultQueryMaxNum = defaultQueryMaxNum;
    }

    /**
     * Enable transient commitLog store pool only if transientStorePoolEnable is true and the FlushDiskType is
     * ASYNC_FLUSH
     *
     * @return <tt>true</tt> or <tt>false</tt>
     */
    public boolean isTransientStorePoolEnable() {
        return transientStorePoolEnable && FlushDiskType.ASYNC_FLUSH == getFlushDiskType()
            && BrokerRole.SLAVE != getBrokerRole();
    }

    public void setTransientStorePoolEnable(final boolean transientStorePoolEnable) {
        this.transientStorePoolEnable = transientStorePoolEnable;
    }

    public int getTransientStorePoolSize() {
        return transientStorePoolSize;
    }

    public void setTransientStorePoolSize(final int transientStorePoolSize) {
        this.transientStorePoolSize = transientStorePoolSize;
    }

    public int getCommitIntervalCommitLog() {
        return commitIntervalCommitLog;
    }

    public void setCommitIntervalCommitLog(final int commitIntervalCommitLog) {
        this.commitIntervalCommitLog = commitIntervalCommitLog;
    }

    public boolean isFastFailIfNoBufferInStorePool() {
        return fastFailIfNoBufferInStorePool;
    }

    public void setFastFailIfNoBufferInStorePool(final boolean fastFailIfNoBufferInStorePool) {
        this.fastFailIfNoBufferInStorePool = fastFailIfNoBufferInStorePool;
    }

    public boolean isUseReentrantLockWhenPutMessage() {
        return useReentrantLockWhenPutMessage;
    }

    public void setUseReentrantLockWhenPutMessage(final boolean useReentrantLockWhenPutMessage) {
        this.useReentrantLockWhenPutMessage = useReentrantLockWhenPutMessage;
    }

    public int getCommitCommitLogLeastPages() {
        return commitCommitLogLeastPages;
    }

    public void setCommitCommitLogLeastPages(final int commitCommitLogLeastPages) {
        this.commitCommitLogLeastPages = commitCommitLogLeastPages;
    }

    public int getCommitCommitLogThoroughInterval() {
        return commitCommitLogThoroughInterval;
    }

    public void setCommitCommitLogThoroughInterval(final int commitCommitLogThoroughInterval) {
        this.commitCommitLogThoroughInterval = commitCommitLogThoroughInterval;
    }

    public String getReadOnlyCommitLogStorePaths() {
        return readOnlyCommitLogStorePaths;
    }

    public void setReadOnlyCommitLogStorePaths(String readOnlyCommitLogStorePaths) {
        this.readOnlyCommitLogStorePaths = readOnlyCommitLogStorePaths;
    }
    public String getdLegerGroup() {
        return dLegerGroup;
    }

    public void setdLegerGroup(String dLegerGroup) {
        this.dLegerGroup = dLegerGroup;
    }

    public String getdLegerPeers() {
        return dLegerPeers;
    }

    public void setdLegerPeers(String dLegerPeers) {
        this.dLegerPeers = dLegerPeers;
    }

    public String getdLegerSelfId() {
        return dLegerSelfId;
    }

    public void setdLegerSelfId(String dLegerSelfId) {
        this.dLegerSelfId = dLegerSelfId;
    }

    public boolean isEnableDLegerCommitLog() {
        return enableDLegerCommitLog;
    }

    public void setEnableDLegerCommitLog(boolean enableDLegerCommitLog) {
        this.enableDLegerCommitLog = enableDLegerCommitLog;
    }

    public String getPreferredLeaderId() {
        return preferredLeaderId;
    }

    public void setPreferredLeaderId(String preferredLeaderId) {
        this.preferredLeaderId = preferredLeaderId;
    }

    public boolean isEnableBatchPush() {
        return isEnableBatchPush;
    }

    public void setEnableBatchPush(boolean enableBatchPush) {
        isEnableBatchPush = enableBatchPush;
    }

    public boolean isEnableScheduleMessageStats() {
        return enableScheduleMessageStats;
    }

    public void setEnableScheduleMessageStats(boolean enableScheduleMessageStats) {
        this.enableScheduleMessageStats = enableScheduleMessageStats;
    }

    public boolean isEnableLmq() {
        return enableLmq;
    }

    public void setEnableLmq(boolean enableLmq) {
        this.enableLmq = enableLmq;
    }

    public boolean isEnableMultiDispatch() {
        return enableMultiDispatch;
    }

    public void setEnableMultiDispatch(boolean enableMultiDispatch) {
        this.enableMultiDispatch = enableMultiDispatch;
    }

    public int getMaxLmqConsumeQueueNum() {
        return maxLmqConsumeQueueNum;
    }

    public void setMaxLmqConsumeQueueNum(int maxLmqConsumeQueueNum) {
        this.maxLmqConsumeQueueNum = maxLmqConsumeQueueNum;
    }

    public boolean isEnableScheduleAsyncDeliver() {
        return enableScheduleAsyncDeliver;
    }

    public void setEnableScheduleAsyncDeliver(boolean enableScheduleAsyncDeliver) {
        this.enableScheduleAsyncDeliver = enableScheduleAsyncDeliver;
    }

    public int getScheduleAsyncDeliverMaxPendingLimit() {
        return scheduleAsyncDeliverMaxPendingLimit;
    }

    public void setScheduleAsyncDeliverMaxPendingLimit(int scheduleAsyncDeliverMaxPendingLimit) {
        this.scheduleAsyncDeliverMaxPendingLimit = scheduleAsyncDeliverMaxPendingLimit;
    }

    public int getScheduleAsyncDeliverMaxResendNum2Blocked() {
        return scheduleAsyncDeliverMaxResendNum2Blocked;
    }

    public void setScheduleAsyncDeliverMaxResendNum2Blocked(int scheduleAsyncDeliverMaxResendNum2Blocked) {
        this.scheduleAsyncDeliverMaxResendNum2Blocked = scheduleAsyncDeliverMaxResendNum2Blocked;
    }
}
