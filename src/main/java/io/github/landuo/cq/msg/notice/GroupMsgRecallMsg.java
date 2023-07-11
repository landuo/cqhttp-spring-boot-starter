package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群消息撤回
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_recall)
public class GroupMsgRecallMsg extends NoticeMsg {
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 消息发送者QQ号
     */
    private Long userId;
    /**
     * 操作者QQ号
     */
    private Long operatorId;
    /**
     * 被撤回的消息 ID
     */
    private Long messageId;
}
