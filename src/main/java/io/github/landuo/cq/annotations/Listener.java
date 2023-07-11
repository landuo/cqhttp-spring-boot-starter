package io.github.landuo.cq.annotations;

import io.github.landuo.cq.msg.common.BaseMsg;

import java.lang.annotation.*;

/**
 * @author accidia
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Listener {
    Class<? extends BaseMsg> value();
}
