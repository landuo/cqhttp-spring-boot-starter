package io.github.landuo.cq.annotations;

import io.github.landuo.cq.enums.*;

import java.lang.annotation.*;

/**
 * @author accidia
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MsgType {

    PostTypeEnum post_type();

    RequestTypeEnum request_type() default RequestTypeEnum.NULL;

    NoticeTypeEnum notice_type() default NoticeTypeEnum.NULL;

    NoticeSubTypeEnum notice_sub_type() default NoticeSubTypeEnum.NULL;

    MetaEventTypeEnum meta_event_type() default MetaEventTypeEnum.NULL;

    MessageTypeEnum message_type() default MessageTypeEnum.NULL;

}

