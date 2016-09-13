package cn.chatweb.dialect;

/**
 * Created by 哲 on 2016/9/7.
 */
public class DbDialectFactory {

    static DbDialect dbDialect;

    public static DbDialect getDialect(){
        return dbDialect;
    }

    public static void use(String className){
        try {
            Class<?> clazz = Class.forName(className);
            dbDialect = (DbDialect) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
