package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群禁言
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_ban)
public class GroupMuteMsg extends NoticeMsg {
    /**
     * 事件子类型, 分别表示禁言、解除禁言.ban、lift_ban
     */
    private String subType;
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 操作者 QQ 号
     */
    private Long operatorId;
    /**
     * 被禁言 QQ 号 (为全员禁言时为0)
     */
    private Long userId;
    /**
     * 禁言时长, 单位秒 (为全员禁言时为-1)
     */
    private Long duration;
}
