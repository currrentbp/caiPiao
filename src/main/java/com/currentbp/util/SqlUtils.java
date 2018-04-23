package com.currentbp.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * sql的集合类
 *
 * @author current_bp
 * @createTime 20180423
 */
public class SqlUtils {

    public static String sqlInConditionLoad(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("sql拼装条件in函数失败！");
        }
        final StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sb.append("?");
            } else {
                sb.append(",?");
            }

        }
        sb.append(")");
        return sb.toString();
    }
}
