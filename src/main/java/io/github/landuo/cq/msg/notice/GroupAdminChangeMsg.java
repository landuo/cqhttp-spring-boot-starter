package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群管理员变动
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_admin)
public class GroupAdminChangeMsg extends NoticeMsg {
    /**
     * 事件子类型, 分别表示设置和取消管理员.set、unset
     */
    private String subType;
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 管理员QQ号
     */
    private Long userId;
}
