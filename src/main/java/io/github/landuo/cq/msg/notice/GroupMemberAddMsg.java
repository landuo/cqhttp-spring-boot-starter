package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群成员增加
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_increase)
public class GroupMemberAddMsg extends NoticeMsg {
    /**
     * 事件子类型, 分别表示管理员已同意入群、管理员邀请入群.approve、invite
     */
    private String subType;
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 操作者QQ号
     */
    private Long operatorId;
    /**
     * 加入者QQ号
     */
    private Long userId;
}
