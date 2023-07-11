package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群成员名片变更
 * 当名片为空时 card_xx 字段为空字符串, 并不是昵称
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_card)
public class GroupCardChangedMsg extends NoticeMsg {
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 成员ID
     */
    private Long userId;
    /**
     * 新名片
     */
    private String cardNew;
    /**
     * 旧名片
     */
    private String cardOld;
}
