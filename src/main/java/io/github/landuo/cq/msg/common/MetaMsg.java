package io.github.landuo.cq.msg.common;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.PostTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 元事件上报
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.meta_event)
public class MetaMsg extends BaseMsg {
    /**
     * 元事件类型
     */
    private String metaEventType;
}
