package cn.chatweb.annotation;

import cn.chatweb.common.Const;

import java.lang.annotation.*;

/**
 * Created by 哲 on 2016/9/7.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface ActionCache {

    String contentType() default "text/html;charset="+ Const.CHARTSET;

}
