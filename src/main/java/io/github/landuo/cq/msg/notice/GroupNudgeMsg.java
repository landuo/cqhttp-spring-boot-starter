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
 * 群内戳一戳（双击头像）
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.notify,notice_sub_type = NoticeSubTypeEnum.poke)
public class GroupNudgeMsg extends NoticeMsg {
    /**
     * 提示类型
     */
    private String subType = "poke";
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 发送者 QQ 号
     */
    private Long userId;
    /**
     * 被戳者QQ号
     */
    private Long targetId;
}
