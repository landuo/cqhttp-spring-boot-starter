package io.github.landuo.cq.msg.meta;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.MetaEventTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.MetaMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 心跳包
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.meta_event,meta_event_type = MetaEventTypeEnum.heartbeat)
public class HeartBeatMsg extends MetaMsg {
    /**
     * 应用程序状态
     */
    private Status status;
    /**
     * 距离上一次心跳包的时间(单位是毫秒)
     */
    private Long interval;

    @Data
    private static class Status {
        /**
         * 程序是否初始化完毕
         */
        private Boolean appInitialized;
        /**
         * 程序是否可用
         */
        private Boolean appEnabled;
        /**
         * 插件正常(可能为 null)
         */
        private Boolean pluginsGood;
        /**
         * 程序正常
         */
        private Boolean online;
        /**
         * 统计信息
         */
        private StatusStat stat;
    }

    @Data
    private static class StatusStat {
        /**
         * 收包数
         */
        private Long packetReceived;
        /**
         * 发包数
         */
        private Long packetSent;
        /**
         * 丢包数
         */
        private Long packetLost;
        /**
         * 消息接收数
         */
        private Long messageReceived;
        /**
         * 消息发送数
         */
        private Long messageSent;
        /**
         * 连接断开次数
         */
        private Long disconnectTimes;
        /**
         * 连接丢失次数
         */
        private Long lostTimes;
        /**
         * 最后一次消息时间
         */
        private Long lastMessageTime;
    }
}
