package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.BaseMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.client_status)
public class OtherClientStatusChangedMsg extends BaseMsg {
    /**
     * 上报类型
     */
//    private String postType;
    /**
     * 消息类型
     */
    private String noticeType = "client_status";
    /**
     * 客户端信息
     */
    private Object client;
    /**
     * 当前是否在线
     */
    private Boolean online;
}
