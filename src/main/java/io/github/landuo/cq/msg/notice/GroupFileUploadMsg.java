package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群文件上传
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.group_upload)
public class GroupFileUploadMsg extends NoticeMsg {
    /**
     * 群号
     */
    private Long groupId;
    /**
     * 发送者QQ号
     */
    private Long userId;
    /**
     * 文件信息
     */
    private File file;

    @Data
    public class File {
        /**
         * 文件ID
         */
        private String id;
        /**
         * 文件名
         */
        private String name;
        /**
         * 文件大小（字节数）
         */
        private Long size;
        /**
         * busid ( 目前不清楚有什么作用 )
         */
        private Long busid;
    }
}
