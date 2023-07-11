package io.github.landuo.cq.msg.message;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.MessageTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.MessageMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 私聊信息
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.message, message_type = MessageTypeEnum.PRIVATE)
public class PrivateMessageMsg extends MessageMsg {
    /**
     * 接收者 QQ 号
     */
    private Long targetId;
    /**
     * 临时会话来源
     */
    private Integer tempSource;
}
