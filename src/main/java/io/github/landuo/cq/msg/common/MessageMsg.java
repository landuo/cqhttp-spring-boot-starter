package io.github.landuo.cq.msg.common;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.MessageTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.Sender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 消息上报
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.message)
public abstract class MessageMsg extends BaseMsg {
    /**
     * 消息类型. private, group
     */
    private String messageType;
    /**
     * 表示消息的子类型. group, public
     */
    private String subType;
    /**
     * 消息 ID
     */
    private Long messageId;
    /**
     * 发送者 QQ 号
     */
    private Long userId;
    /**
     * 一个消息链
     */
    private String message;
    /**
     * CQ 码格式的消息
     */
    private String rawMessage;
    /**
     * 字体
     */
    private Integer font;
    /**
     * 发送者信息
     */
    private Sender sender;
}
