package io.github.landuo.cq.msg;

import lombok.Data;

/**
 * @author accidia
 */
@Data
public class Sender {
    /**
     * 发送者QQ号
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别, male 或 female 或 unknown
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 当私聊类型为群临时会话时的额外字段:
     */
    private Long groupId;

    // 如果是群聊时还有以下额外信息
    /**
     * 群名片/备注
     */
    private String card;
    /**
     * 地区
     */
    private String area;
    /**
     * 成员等级
     */
    private String level;
    /**
     * 角色, owner 或 admin 或 member
     */
    private String role;
    /**
     * 专属头衔
     */
    private String title;
}
