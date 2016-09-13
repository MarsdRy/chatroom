package cn.chatweb.bean.base;

import cn.chatweb.bean.BaseDomain;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.math.BigInteger;

/**
 * Created by 哲 on 2016/9/8.
 */
public class BaseChatRoomDomain<M extends BaseChatRoomDomain<M>> extends BaseDomain<M> {

    public static final String CACHE_NAME = "room";

    public void removeCache(Object key){
        CacheKit.remove(CACHE_NAME, key);
    }

    public void putCache(Object key, Object value){
        CacheKit.put(CACHE_NAME, key, value);
    }

    public M getCache(Object key){
        return CacheKit.get(CACHE_NAME, key);
    }

    public M getCache(Object key, IDataLoader dataLoader){
        return CacheKit.get(CACHE_NAME, key, dataLoader);
    }

    @Override
    public boolean equals(Object o) {

        if(o == null){
            return false;
        }

        if(!(o instanceof BaseChatRoomDomain<?>)){
            return false;
        }

        BaseChatRoomDomain<?> m = (BaseChatRoomDomain<?>) o;
        if(m.getId() == null){
            return false;
        }

        return m.getId().compareTo(getId()) == 0;
    }

    /** 主键ID*/
    public void setId(BigInteger id){
        set("id", id);
    }

    public BigInteger getId() {
        Object id = get("id");
        if(id == null){
            return null;
        }
        return id instanceof BigInteger ? (BigInteger)id : new BigInteger(id.toString());
    }

    public void setRoomName(String roomName){
        set("roomName", roomName);
    }

    /** 房间名称*/
    public String getRoomName(){
        return get("roomName");
    }

    /** 是否存在房间密码*/
    public boolean isHasPwd(){
        Object o = get("roomPwd");
        if(o == null){
            return false;
        }
        return true;
    }

    public void setPassword(String password){
        set("roomPwd",password);
    }

    /** 房间进入密码*/
    public String getPassword(){
        return get("roomPwd");
    }

    public void setCreated(java.util.Date created) {
        set("created", created);
    }

    /** 创建时间*/
    public java.util.Date getCreated() {
        return get("created");
    }

    public void setActivated(int activated) {
        set("activated", activated);
    }

    /** 活动状态*/
    public int getActivated() {
        return get("activated");
    }

}
