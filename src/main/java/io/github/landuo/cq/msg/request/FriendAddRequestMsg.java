package io.github.landuo.cq.msg.request;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.enums.RequestTypeEnum;
import io.github.landuo.cq.msg.common.RequestMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 加好友请求
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.request,request_type = RequestTypeEnum.friend)
public class FriendAddRequestMsg extends RequestMsg {
    /**
     * 发送请求的 QQ 号
     */
    private Long userId;
    /**
     * 验证信息
     */
    private String comment;
    /**
     * 请求 flag, 在调用处理请求的 API 时需要传入
     */
    private String flag;
}
