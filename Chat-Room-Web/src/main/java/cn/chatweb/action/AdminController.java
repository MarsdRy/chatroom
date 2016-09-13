package cn.chatweb.action;

import cn.chatweb.annotation.RouterMapping;
import cn.chatweb.bean.UserDomain;
import cn.chatweb.common.Const;
import cn.chatweb.interception.ActionCacheClearInterceptor;
import cn.chatweb.service.UserService;
import cn.chatweb.utils.CookieUtils;
import cn.chatweb.utils.EncryptUtils;
import cn.chatweb.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by 哲 on 2016/9/8.
 */

@RouterMapping(url = "/admin", viewPath = "/WEB-INF/admin")
public class AdminController extends BaseController{

    public void index(){
        render("login.html");
    }

    @Before(ActionCacheClearInterceptor.class)
    public void login(){
        String username = getPara("username");
        String password = getPara("password");

        if(!StringUtils.areNotEmpty(username, password)){
            render("login.html");
            return;
        }

        UserDomain user = UserService.me().getUserByName(username);
        if(user == null){
            renderAjaxResultForError("没有该用户");
            return;
        }

        if (EncryptUtils.verlifyUser(user.getPassword(), user.getSalt(), password) && user.isAdministrator()) {
            CookieUtils.put(this, Const.COOKIE_LOGINED_USER, user.getId().toString());
            renderAjaxResultForSuccess("登陆成功");
        } else {
            renderAjaxResultForError("密码错误");
        }

    }

}
