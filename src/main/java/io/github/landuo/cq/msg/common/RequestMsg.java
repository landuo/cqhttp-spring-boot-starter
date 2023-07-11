package io.github.landuo.cq.msg.common;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.PostTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 请求上报
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.request)
public class RequestMsg extends BaseMsg {
    /**
     * 请求类型
     */
    private String requestType;
}
