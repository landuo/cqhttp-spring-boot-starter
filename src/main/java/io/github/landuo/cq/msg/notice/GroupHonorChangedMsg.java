package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeSubTypeEnum;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群成员荣誉变更
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.notify,notice_sub_type = NoticeSubTypeEnum.honor)
public class GroupHonorChangedMsg extends NoticeMsg {
    /**
     * 提示类型
     */
    private String subType = "honor";
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 成员ID
     */
    private Long userId;
    /**
     * 荣誉类型
     * talkative:龙王 performer:群聊之火 emotion:快乐源泉
     */
    private String targetId;
}
