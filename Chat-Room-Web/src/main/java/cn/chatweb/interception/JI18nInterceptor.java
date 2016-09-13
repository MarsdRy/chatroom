package cn.chatweb.interception;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.i18n.Res;
import com.jfinal.kit.StrKit;

/**
 * Created by 哲 on 2016/9/7.
 */
public class JI18nInterceptor extends I18nInterceptor {

    @Override
    public void intercept(Invocation inv) {

        Controller c = inv.getController();
        String localeParaname = getLocaleParaName();
        String locale = c.getPara(localeParaname);

        if(StrKit.notBlank(locale)){
            c.setCookie(localeParaname, locale, Const.DEFAULT_I18N_MAX_AGE_OF_COOKIE);
        }else{
            locale = c.getCookie(localeParaname);
            if(StrKit.isBlank(locale)){
                locale = I18n.toLocale(c.getRequest().getLocale());
            }
        }

        Res res = I18n.use(getBaseName(), locale);
        c.setAttr(getResName(), res);

        inv.invoke();
    }
}
