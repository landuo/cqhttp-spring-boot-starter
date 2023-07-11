package io.github.landuo.cq.msg.request;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.enums.RequestTypeEnum;
import io.github.landuo.cq.msg.common.RequestMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 加群请求／邀请
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.request,request_type = RequestTypeEnum.group)
public class GroupAddRequestMsg extends RequestMsg {
    /**
     * 请求子类型, 分别表示加群请求、邀请登录号入群.add、invite
     */
    private String subType;
    /**
     * 群号
     */
    private Long groupId;
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
