package io.github.landuo.cq.msg.common;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.PostTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 通知上报
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MsgType(post_type = PostTypeEnum.notice)
@Data
public class NoticeMsg extends BaseMsg {
    /**
     * 通知类型
     */
    private String noticeType;
}
