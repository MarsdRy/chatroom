package cn.chatweb.cache;

import com.jfinal.plugin.ehcache.CacheKit;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 哲 on 2016/9/7.
 */
public class ActionCacheManager {

    private static String USE_JCACHE = "_use_jcache__";
    private static String USE_JCACHE_KEY = "_use_jcache_key__";
    private static String USE_JCACHE_CONTENT_TYPE = "_use_jcache_content_type__";

    public static String CACHE_NAME = "action";

    public static void clearCache(){
        CacheKit.removeAll(CACHE_NAME);
    }

    public static String getCache(String key){
        return CacheKit.get(CACHE_NAME, key);
    }

    public static void putCache(HttpServletRequest request, Object object){
        CacheKit.put(CACHE_NAME, getCacheKey(request), object);
    }

    public static void enableCache(HttpServletRequest request){
        request.setAttribute(USE_JCACHE, true);
    }

    public static boolean isEnableCache(HttpServletRequest request){
        return (Boolean)(request.getAttribute(USE_JCACHE) == null ? false : request.getAttribute(USE_JCACHE));
    }

    public static void setCacheKey(HttpServletRequest request, String key){
        request.setAttribute(USE_JCACHE_KEY, key);
    }

    public static String getCacheKey(HttpServletRequest request) {
        return (String) request.getAttribute(USE_JCACHE_KEY);
    }

    public static void setCacheContentType(HttpServletRequest request, String contentType){
        request.setAttribute(USE_JCACHE_CONTENT_TYPE, contentType);
    }

    public static String getCacheContentType(HttpServletRequest request){
        return (String) request.getAttribute(USE_JCACHE_CONTENT_TYPE);
    }

}
