package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群成员头衔变更
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupTitleChangedMsg extends NoticeMsg {
    /**
     * 提示类型
     */
    private String subType = "title";
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 变更头衔的用户 QQ 号
     */
    private Long userId;
    /**
     * 获得的新头衔
     */
    private String title;
}
