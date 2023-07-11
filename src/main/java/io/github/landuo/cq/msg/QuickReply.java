package io.github.landuo.cq.msg;

import lombok.Builder;
import lombok.Data;

/**
 * 快速回复
 * @author accidia
 */
@Data
@Builder
public class QuickReply {
    /**
     * 要回复的内容
     */
    private String reply;
    /**
     * 消息内容是否作为纯文本发送 ( 即不解析 CQ 码 ) , 只在 reply 字段是字符串时有效
     */
    private Boolean autoEscape;
    /**
     * 是否要在回复开头 at 发送者 ( 自动添加 ) , 发送者是匿名用户时无效
     */
    private Boolean atSender;
    /**
     * 撤回该条消息
     */
    private Boolean delete;
    /**
     * 把发送者踢出群组 ( 需要登录号权限足够 ) , 不拒绝此人后续加群请求, 发送者是匿名用户时无效
     */
    private Boolean kick;
    /**
     * 禁言该消息发送者, 对匿名用户也有效
     */
    private Boolean ban;
    /**
     * 若要执行禁言操作时的禁言时长	30 分钟
     */
    private Integer banDuration;

}
