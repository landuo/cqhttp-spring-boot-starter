package io.github.landuo.cq.msg;

import lombok.Data;

/**
 * @author accidia
 */
@Data
public class QuickRequestReply {
    /**
     * 是否同意请求／邀请
     */
    private Boolean approve;
    /**
     * 添加后的好友备注 (仅在同意时有效) (加好友的回复)
     */
    private String remark;
    /**
     * 拒绝理由 ( 仅在拒绝时有效 )
     */
    private String reason;
}
