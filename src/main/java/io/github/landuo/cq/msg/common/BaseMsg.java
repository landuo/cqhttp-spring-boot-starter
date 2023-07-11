package io.github.landuo.cq.msg.common;

import io.github.landuo.cq.enums.PostTypeEnum;
import lombok.Data;

/**
 * 通用数据
 * @author accidia
 */
@Data
public abstract class BaseMsg {
    /**
     * 事件发生的unix时间戳
     */
     Long time;
    /**
     * 收到事件的机器人的 QQ 号
     */
     Long selfId;
    /**
     * 表示该上报的类型, 消息, 消息发送, 请求, 通知, 或元事件
     */
     PostTypeEnum postType;
}
