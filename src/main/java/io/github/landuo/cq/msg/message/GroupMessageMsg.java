package io.github.landuo.cq.msg.message;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.MessageTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.MessageMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群信息
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.message, message_type = MessageTypeEnum.GROUP)
public class GroupMessageMsg extends MessageMsg {
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 匿名信息, 如果不是匿名消息则为 null
     */
    private Anonymous anonymous;

    @Data
    private static class Anonymous {
        /**
         * 匿名用户 ID
         */
        private Long id;
        /**
         * 匿名用户名称
         */
        private String name;
        /**
         * 匿名用户 flag, 在调用禁言 API 时需要传入
         */
        private String flag;
    }

}
