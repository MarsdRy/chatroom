package cn.chatweb.core;

import cn.chatweb.annotation.RouterMapping;
import cn.chatweb.annotation.Table;
import cn.chatweb.common.Const;
import cn.chatweb.dialect.DbDialect;
import cn.chatweb.dialect.DbDialectFactory;
import cn.chatweb.interception.ActionCacheClearInterceptor;
import cn.chatweb.utils.ClassScaner;
import cn.chatweb.utils.StringUtils;
import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by 哲 on 2016/9/7.
 */
public class WebConfigFilter extends JFinalConfig{

    private static Log log = Log.getLog(WebConfigFilter.class);

    @Override
    public void configConstant(Constants constants) {
        log.info("configure is starting...");
        PropKit.use("jdbc.properties");
        constants.setDevMode(PropKit.getBoolean("dev_mode", false));
        constants.setViewType(ViewType.FREE_MARKER);
        constants.setI18nDefaultLocale("language");
        constants.setEncoding(Const.CHARTSET);
        constants.setMaxPostSize(1024 * 1024 * 200);
    }

    @Override
    public void configRoute(Routes routs) {
        List<Class<Controller>> controllerClassList = ClassScaner.scanSubClass(Controller.class);
        if(controllerClassList!=null && controllerClassList.size()>0){
            for(Class clazz : controllerClassList){
                RouterMapping urlMapping = (RouterMapping) clazz.getAnnotation(RouterMapping.class);
                if(null != urlMapping && StringUtils.isNotBlank(urlMapping.url())){
                    if(StrKit.notBlank(urlMapping.viewPath())){
                        routs.add(urlMapping.url(), (Class<? extends Controller>) clazz, urlMapping.viewPath());
                    }else{
                        routs.add(urlMapping.url(), (Class<? extends Controller>) clazz);
                    }
                }
            }
        }
    }

    @Override
    public void configPlugin(Plugins plugins) {
        plugins.add(createEhCachePlugin());

        DruidPlugin druidPlugin = createDruidPlugin();
        plugins.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = createRecordPlugin(druidPlugin);
        plugins.add(activeRecordPlugin);
    }

    private ActiveRecordPlugin createRecordPlugin(IDataSourceProvider dsp) {
        ActiveRecordPlugin arPlugin = new ActiveRecordPlugin(dsp);
        List<Class<Model>> modelClassList = ClassScaner.scanSubClass(Model.class);

        if(modelClassList != null){
            String tablePrefix = getProperty("db_tablePrefix");
            tablePrefix = (StrKit.isBlank(tablePrefix)) ? "" : tablePrefix;
            for (Class<Model> clazz : modelClassList){
                Table table = clazz.getAnnotation(Table.class);
                if(table == null){
                    continue;
                }
                String tablename = tablePrefix + table.tableName();
                if(StringUtils.isNotBlank(table.primaryKey())){
                    arPlugin.addMapping(tablename, table.primaryKey(), (Class<? extends Model<?>>) clazz);
                }else{
                    arPlugin.addMapping(tablename, (Class<? extends Model<?>>) clazz);
                }
                DbDialect.mapping(clazz.getSimpleName().toString(), tablename);
            }
        }

        arPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
        return arPlugin;
    }

    private DruidPlugin createDruidPlugin() {
        loadPropertyFile("jdbc.properties");

        String db_host = getProperty("db_host").trim();

        String db_host_port = getProperty("db_host_port");
        db_host_port = StringUtils.isNotBlank(db_host_port) ? db_host_port.trim() : "3306";

        String db_name = getProperty("db_name").trim();
        String db_user = getProperty("db_user").trim();
        String db_password = getProperty("db_password").trim();

        return DbDialectFactory.getDialect().createDuidPlugin(db_host, db_host_port, db_name, db_user, db_password);

    }

    private IPlugin createEhCachePlugin() {
        String ehcacheDiskStorePath = PathKit.getWebRootPath();
        File pathFile = new File(ehcacheDiskStorePath, ".ehcache");
        log.info("file absolute path :" + pathFile.getAbsolutePath());
        Configuration cfg = ConfigurationFactory.parseConfiguration();
        cfg.addDiskStore(new DiskStoreConfiguration().path(pathFile.getAbsolutePath()));
        return new EhCachePlugin(cfg);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new ActionCacheClearInterceptor());
        //interceptors.add(new JI18nInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    //public abstract void onJfinalStartAfter();

    //public abstract void onJfinalStartBefore();
}
