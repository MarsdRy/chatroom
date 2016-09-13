package cn.chatweb.annotation;

import java.lang.annotation.*;

/**
 * Created by 哲 on 2016/9/9.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Table {

    String tableName();

    String primaryKey() default "";

}
