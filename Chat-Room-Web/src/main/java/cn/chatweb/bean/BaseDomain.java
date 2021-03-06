package cn.chatweb.bean;

import cn.chatweb.dialect.DbDialectFactory;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 哲 on 2016/9/7.
 */
public class BaseDomain<M extends BaseDomain<M>> extends Model<M> {

    public Page<M> doPaginate(int pageNumber, int pageSize) {
        return doPaginate(pageNumber, pageSize, null);
    }

    public Page<M> doPaginate(int pageNumber, int pageSize, String whereSql, Object... params) {
        String from = DbDialectFactory.getDialect().forPaginateFrom(getTableName(), whereSql);
        return paginate(pageNumber, pageSize, "SELECT *", from, params);
    }

    public Page<M> doPaginateByCache(String cacheName, Object key, int pageNumber, int pageSize) {
        return doPaginateByCache(cacheName, key, pageNumber, pageSize, null);
    }

    public Page<M> doPaginateByCache(String cacheName, Object key, int pageNumber, int pageSize, String whereSql,
                                     Object... params) {
        String from = DbDialectFactory.getDialect().forPaginateFrom(getTableName(), whereSql);
        return paginateByCache(cacheName, key, pageNumber, pageSize, "SELECT *", from, params);
    }

    private String createSelectSql() {
        return DbDialectFactory.getDialect().forSelect(getTableName());
    }

    private String createDeleteSql() {
        return DbDialectFactory.getDialect().forDelete(getTableName());
    }

    private String createSelectWhereSql() {
        return createSelectSql() + " WHERE ";
    }

    public List<M> doFind() {
        return find(createSelectSql());
    }

    public List<M> doFind(String where) {
        return find(createSelectWhereSql() + where);
    }

    public List<M> doFind(String where, Object... params) {
        return find(createSelectWhereSql() + where, params);
    }

    public List<M> doFindByCache(String cacheName, Object key) {
        return findByCache(cacheName, key, createSelectSql());
    }

    public List<M> doFindByCache(String cacheName, Object key, String where) {
        return findByCache(cacheName, key, createSelectWhereSql() + where);
    }

    public List<M> doFindByCache(String cacheName, Object key, String where, Object... params) {
        return findByCache(cacheName, key, createSelectWhereSql() + where, params);
    }

    public M doFindFirst(String where) {
        return findFirst(createSelectWhereSql() + where);
    }

    public M doFindFirst(String where, Object... params) {
        return findFirst(createSelectWhereSql() + where, params);
    }

    public M doFindFirstByCache(String cacheName, Object key, String where) {
        return findFirstByCache(cacheName, key, createSelectWhereSql() + where);
    }

    public M doFindFirstByCache(String cacheName, Object key, String where, Object... params) {
        return findFirstByCache(cacheName, key, createSelectWhereSql() + where, params);
    }

    public Long doFindCount() {
        return doFindCount(null);
    }

    public Long doFindCount(String whereSQL, Object... params) {
        String sql = DbDialectFactory.getDialect().forSelectCount(getTableName());
        final StringBuilder sqlBuilder = new StringBuilder(sql);
        if (null != whereSQL && !"".equals(whereSQL.trim())) {
            sqlBuilder.append(" WHERE ").append(whereSQL);
        }
        return Db.queryLong(tc(sqlBuilder.toString()), params);
    }

    public long doFindCountByCache(String cacheName, Object key) {
        return doFindCountByCache(cacheName, key, null);
    }

    public long doFindCountByCache(String cacheName, Object key, String whereSQL, final Object... params) {
        String sql = DbDialectFactory.getDialect().forSelectCount(getTableName());
        final StringBuilder sqlBuilder = new StringBuilder(sql);
        if (null != whereSQL && !"".equals(whereSQL.trim())) {
            sqlBuilder.append(" WHERE ").append(whereSQL);
        }
        return CacheKit.get(cacheName, key, new IDataLoader() {
            @Override
            public Object load() {
                return Db.queryLong(tc(sqlBuilder.toString()), params);
            }
        });
    }

    public int doDelete(String where, Object... objs) {
        String sql = createDeleteSql() + " WHERE " + where;
        return Db.update(sql, objs);
    }

    public boolean saveOrUpdate() {
        if (null == get(getPrimaryKey())) {
            return this.save();
        }
        return this.update();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!(o instanceof BaseDomain<?>))
            return false;

        if (((BaseDomain<?>) o).get("id") == null)
            return false;

        return ((BaseDomain<?>) o).get("id").equals(get("id"));
    }

    public String getTableName() {
        return TableMapping.me().getTable(getUsefulClass()).getName();
    }

    public String getPrimaryKey() {
        String[] primaryKeys = getPrimaryKeys();
        if (null != primaryKeys && primaryKeys.length == 1) {
            return primaryKeys[0];
        }
        throw new RuntimeException(String.format("get PrimaryKey is error in[%s]", getClass()));
    }

    public String[] getPrimaryKeys() {
        Table t = TableMapping.me().getTable(getUsefulClass());
        if (t == null) {
            throw new RuntimeException("can't get table of " + getUsefulClass() + " , maybe jpress install incorrect");
        }
        return t.getPrimaryKey();
    }

    public boolean hasColumn(String columnLabel) {
        return TableMapping.me().getTable(getUsefulClass()).hasColumnLabel(columnLabel);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Class<? extends BaseDomain> getUsefulClass() {
        Class c = getClass();
        return c.getName().indexOf("EnhancerByCGLIB") == -1 ? c : c.getSuperclass();
        // com.demo.blog.Blog$$EnhancerByCGLIB$$69a17158
    }

    private static String tc(String sql) {
        return DbDialectFactory.getDialect().doTableConvert(sql);
    }

    // -----------------------------Override----------------------------
    @Override
    public Page<M> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.paginate(pageNumber, pageSize, tc(select), tc(sqlExceptSelect), paras);
    }

    @Override
    public Page<M> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect,
                            Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.paginate(pageNumber, pageSize, isGroupBySql, tc(select), tc(sqlExceptSelect), paras);
    }

    @Override
    public Page<M> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        // TODO Auto-generated method stub
        return super.paginate(pageNumber, pageSize, tc(select), tc(sqlExceptSelect));
    }

    @Override
    public List<M> find(String sql, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.find(tc(sql), paras);
    }

    @Override
    public List<M> find(String sql) {
        // TODO Auto-generated method stub
        return super.find(tc(sql));
    }

    @Override
    public M findFirst(String sql, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.findFirst(tc(sql), paras);
    }

    @Override
    public M findFirst(String sql) {
        // TODO Auto-generated method stub
        return super.findFirst(tc(sql));
    }

    @Override
    public List<M> findByCache(String cacheName, Object key, String sql, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.findByCache(cacheName, key, tc(sql), paras);
    }

    @Override
    public List<M> findByCache(String cacheName, Object key, String sql) {
        // TODO Auto-generated method stub
        return super.findByCache(cacheName, key, tc(sql));
    }

    @Override
    public M findFirstByCache(String cacheName, Object key, String sql, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.findFirstByCache(cacheName, key, tc(sql), paras);
    }

    @Override
    public M findFirstByCache(String cacheName, Object key, String sql) {
        // TODO Auto-generated method stub
        return super.findFirstByCache(cacheName, key, tc(sql));
    }

    @Override
    public Page<M> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize, String select,
                                   String sqlExceptSelect, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.paginateByCache(cacheName, key, pageNumber, pageSize, tc(select), tc(sqlExceptSelect), paras);
    }

    @Override
    public Page<M> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize, boolean isGroupBySql,
                                   String select, String sqlExceptSelect, Object... paras) {
        // TODO Auto-generated method stub
        debugPrintParas(paras);
        return super.paginateByCache(cacheName, key, pageNumber, pageSize, isGroupBySql, tc(select),
                tc(sqlExceptSelect), paras);
    }

    @Override
    public Page<M> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize, String select,
                                   String sqlExceptSelect) {
        // TODO Auto-generated method stub
        return super.paginateByCache(cacheName, key, pageNumber, pageSize, tc(select), tc(sqlExceptSelect));
    }

    private void debugPrintParas(Object... objects) {
        if (JFinal.me().getConstants().getDevMode()) {
            System.out.println("\r\n---------------Paras: " + Arrays.toString(objects) + "----------------");
        }
    }
}
