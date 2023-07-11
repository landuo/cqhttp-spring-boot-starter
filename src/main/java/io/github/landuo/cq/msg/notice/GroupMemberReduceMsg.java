package io.github.landuo.cq.msg.notice;


import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群成员减少
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_decrease)
public class GroupMemberReduceMsg extends NoticeMsg {
    /**
     * 事件子类型, 分别表示主动退群、成员被踢、登录号被踢.leave、kick、kick_me
     */
    private String subType;
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 操作者 QQ 号 ( 如果是主动退群, 则和 user_id 相同 )
     */
    private Long operatorId;
    /**
     * 离开者QQ号
     */
    private Long userId;
}
