package cn.chatweb.service;

import cn.chatweb.bean.UserDomain;
import com.jfinal.plugin.ehcache.IDataLoader;

/**
 * Created by 哲 on 2016/9/8.
 */
public class UserService extends BaseService {

    public static final UserDomain USERDAO = new UserDomain();
    public static final UserService USERSERVICE = new UserService();

    public static UserService me(){
        return USERSERVICE;
    }

    public UserDomain getUserByName(final String username){
        return USERDAO.getCache(username, new IDataLoader() {
            @Override
            public Object load() {
                return USERDAO.doFindFirst("username = ?", username);
            }
        });
    }

}
