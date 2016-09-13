package cn.chatweb.core;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.ehcache.CacheKit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by 哲 on 2016/9/8.
 */
public class JSession implements HttpSession {

    final Controller controller;
    private static final int TIME = 60 * 60 * 24 * 7;

    public JSession(Controller controller) {
        this.controller = controller;
    }

    private void doPut(String key, Object value){
        CacheKit.put("session", key + tryToGetJsessionId(), value);
    }

    private void doRemove(String key){
        CacheKit.remove("session", key + tryToGetJsessionId());
    }

    private Object doGet(String key){
        return CacheKit.get("session", key + tryToGetJsessionId());
    }

    private String tryToGetJsessionId() {
        String sessionId = controller.getCookie("JPSESSIONID");
        if(sessionId==null || "".equals(sessionId.trim())){
            sessionId = UUID.randomUUID().toString().replace("-","");
            controller.setCookie("JPSESSIONID",sessionId,TIME,true);
        }
        return sessionId;
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public String getId() {
        return tryToGetJsessionId();
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return JFinal.me().getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int i) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        throw new RuntimeException("getAttributeNames method not finished.");
    }

    @Override
    public Object getAttribute(String key) {
        return doGet(key);
    }

    @Override
    public Object getValue(String key) {
        return doGet(key);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        throw new RuntimeException("getAttributeNames method not finished.");
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String key, Object value) {
        doPut(key, value);
    }

    @Override
    public void putValue(String key, Object value) {
        doPut(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        doRemove(key);
    }

    @Override
    public void removeValue(String key) {
        doRemove(key);
    }

    @Override
    public void invalidate() {

    }

    @Override
    public boolean isNew() {
        return false;
    }
}
