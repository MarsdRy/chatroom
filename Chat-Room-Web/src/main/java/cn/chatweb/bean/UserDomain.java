package cn.chatweb.bean;

import cn.chatweb.annotation.Table;
import cn.chatweb.bean.base.BaseUserDomain;

import java.util.Date;

/**
 * Created by 哲 on 2016/9/7.
 */
@Table(tableName = "User", primaryKey = "id")
public class UserDomain extends BaseUserDomain<UserDomain>{

    public static final long serialVersionUID = 1L;

    public static final String ROLE_ADMINISTRATOR = "administrator";
    public static final String STATUS_NORMAL = "normal";
    public static final String STATUS_FROZEN = "frozen";

    public boolean isAdministrator(){
        return ROLE_ADMINISTRATOR.equalsIgnoreCase(getRole());
    }

    public boolean isFrozen(){
        return STATUS_FROZEN.equalsIgnoreCase(getStatus());
    }

    @Override
    public boolean save(){
        if(getCreated() == null){
            setCreated(new Date());
        }
        return super.save();
    }

    @Override
    public boolean update(){
        removeCache(getId());
        removeCache(getMobile());
        removeCache(getUsername());
        removeCache(getEmail());
        return super.update();
    }
}
