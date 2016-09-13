package cn.chatweb.service;

import cn.chatweb.utils.StringUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by 哲 on 2016/9/8.
 */
public class BaseService {

    protected static boolean appendWhereOrAnd(StringBuilder stringBuilder, boolean needWhere){
        if(needWhere){
            stringBuilder.append(" WHERE");
        }else{
            stringBuilder.append(" AND ");
        }
        return false;
    }

    protected static boolean appendIfNotEmpty(StringBuilder builder, String colName, String value, List<Object> params, boolean needWhere) {
        if(StringUtils.isNotBlank(value)){
            needWhere = appendWhereOrAnd(builder, needWhere);
            builder.append(" ").append(colName).append(" = ? ");
            params.add(value);
        }
        return needWhere;
    }

    protected static boolean appendIfNotEmpty(StringBuilder builder, String colName, BigInteger value, List<Object> params, boolean needWhere) {
        if(value!=null && value.compareTo(BigInteger.ZERO) > 0){
            needWhere = appendWhereOrAnd(builder, needWhere);
            builder.append(" ").append(colName).append(" = ? ");
            params.add(value);
        }
        return needWhere;
    }

    protected static boolean appendIfNotEmpty(StringBuilder builder, String colName, Object[] array, List<Object> params, boolean needWhere) {
        if (null != array && array.length > 0) {
            needWhere = appendWhereOrAnd(builder, needWhere);
            builder.append(" (");
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    builder.append(" ").append(colName).append(" = ? ");
                } else {
                    builder.append(" OR ").append(colName).append(" = ? ");
                }
                params.add(array[i]);
            }
            builder.append(" ) ");
        }
        return needWhere;
    }

    protected static boolean appendIfNotEmptyWithLike(StringBuilder builder, String colName, Object[] array, List<Object> params, boolean needWhere) {
        if (null != array && array.length > 0) {
            needWhere = appendWhereOrAnd(builder, needWhere);
            builder.append(" (");
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    builder.append(" ").append(colName).append(" like ? ");
                } else {
                    builder.append(" OR ").append(colName).append(" like ? ");
                }
                params.add("%"+array[i]+"%");
            }
            builder.append(" ) ");
        }
        return needWhere;
    }

}
