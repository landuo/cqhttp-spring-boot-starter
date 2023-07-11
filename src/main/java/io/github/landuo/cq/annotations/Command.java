package io.github.landuo.cq.annotations;

import java.lang.annotation.*;

/**
 * @author accidia
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {
    String value() default "";

    String sample() default "";

}
