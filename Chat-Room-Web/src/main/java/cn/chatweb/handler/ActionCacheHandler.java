package cn.chatweb.handler;

import cn.chatweb.annotation.ActionCache;
import cn.chatweb.cache.ActionCacheManager;
import cn.chatweb.utils.StringUtils;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.log.Log;
import com.jfinal.render.RenderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 哲 on 2016/9/7.
 */
public class ActionCacheHandler extends Handler {

    static Log log = Log.getLog(ActionCacheHandler.class);
    static String[] urlPara = { null };

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] ishandler) {

        log.info("ActionCacheHandle Enter...");
        log.info("Target : "+ target);

        Action action = JFinal.me().getAction(target, urlPara);
        if(action == null){
            next.handle(target, request, response, ishandler);
            return;
        }

        ActionCache actionCache = action.getMethod().getAnnotation(ActionCache.class);
        if(actionCache == null){
            actionCache = action.getControllerClass().getAnnotation(ActionCache.class);
            if(actionCache == null){
                next.handle(target, request, response, ishandler);
                return;
            }
        }

        String originalTarget = (String) request.getAttribute("_original_target");
        String cacheKey = StringUtils.isNotBlank(originalTarget) ? originalTarget : target;

        String queryString = request.getQueryString();
        if(queryString != null){
            queryString = "?" + queryString;
            cacheKey += queryString;
        }

        ActionCacheManager.enableCache(request);
        ActionCacheManager.setCacheKey(request, cacheKey);
        ActionCacheManager.setCacheContentType(request, actionCache.contentType());

        String renderContent = ActionCacheManager.getCache(cacheKey);
        if(renderContent!=null){
            response.setContentType(actionCache.contentType());

            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
                printWriter.write(renderContent);
                ishandler[0] = true;
            } catch (IOException e) {
                RenderFactory.me().getErrorRender(500).setContext(request, response, action.getViewPath()).render();
            } finally {
                if(printWriter != null)
                    printWriter.close();
            }
        }else {
            next.handle(target, request, response, ishandler);
        }
    }
}
