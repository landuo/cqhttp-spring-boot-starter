package io.github.landuo.cq.msg.notice;

import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.enums.NoticeTypeEnum;
import io.github.landuo.cq.enums.PostTypeEnum;
import io.github.landuo.cq.msg.common.NoticeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 接收到离线文件
 *
 * @author accidia
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@MsgType(post_type = PostTypeEnum.notice, notice_type = NoticeTypeEnum.offline_file)
public class OfflineFileMsg extends NoticeMsg {
    /**
     * 发送者QQ号
     */
    private Long userId;
    /**
     * 加入者QQ号
     */
    private File file;

    @Data
    private static class File {
        /**
         * 文件名
         */
        private String name;
        /**
         * 文件大小
         */
        private Long size;
        /**
         * 下载链接
         */
        private String url;
    }
}
