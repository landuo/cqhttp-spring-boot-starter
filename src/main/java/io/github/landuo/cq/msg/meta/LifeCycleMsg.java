package io.github.landuo.cq.msg.meta;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.MetaEventTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.MetaMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 生命周期
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.meta_event, meta_event_type = MetaEventTypeEnum.lifecycle)
public class LifeCycleMsg extends MetaMsg {
    /**
     * 子类型. enable, disable, connect
     */
    private String subType;
}
