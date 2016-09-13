package cn.chatweb.interception;

import cn.chatweb.cache.ActionCacheManager;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by 哲 on 2016/9/8.
 */
public class ActionCacheClearInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        invocation.invoke();
        ActionCacheManager.clearCache();
    }
}
