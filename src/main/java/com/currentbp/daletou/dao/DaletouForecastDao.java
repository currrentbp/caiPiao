package com.currentbp.daletou.dao;

import com.currentbp.daletou.entity.DaletouForecast;
import com.currentbp.jdbc.MyJdbcTemplate;
import com.currentbp.util.SqlUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 预测表的相关业务：预测并添加到数据库、创建表结构
 *
 * @author current_bp
 * @createTime 20180426
 */
@Repository
public class DaletouForecastDao {
    @Resource
    private MyJdbcTemplate myJdbcTemplate;

    private static final String BASE_TABLE = "daletou_forecast_";
    private static final String BASE_COLUMN = " id,forecast,used_count ";


    private static RowMapper<DaletouForecast> rowMapper = new BeanPropertyRowMapper(DaletouForecast.class);

    /**
     * 创建一个表
     *
     * @param daletouId 大乐透ID
     */
    public void createTable(Integer daletouId) {
        String sql = "create table `caipiao`.daletou_forecast_" + daletouId + "(\n" +
                "\t`id` INT(11) NOT NULL  AUTO_INCREMENT COMMENT 'id',\n" +
                "\t`forecast` CHAR(30) NOT NULL COMMENT '预期的号码',\n" +
                "\t`used_count` INT(11) NOT NULL COMMENT '使用数量',\n" +
                "\tPRIMARY KEY (`id`)\n" +
                ")\n" +
                "COMMENT='大乐透'\n" +
                "COLLATE='utf8_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=1\n";
        int update = myJdbcTemplate.update(sql);
    }


    /**
     * 批量插入大乐透预测数据
     *
     * @param daletouId        大乐透ID
     * @param daletouForecasts 大乐透预测数据
     */
    public void batchInsert(final Integer daletouId, List<DaletouForecast> daletouForecasts) {
        final String INSERT_DALETOUFORECAST_SQL = "insert into " + BASE_TABLE + daletouId + "(forecast,used_count) values(?,?)";
        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, daletouForecasts.get(i).getForecast());
                ps.setObject(2, 0);
            }

            @Override
            public int getBatchSize() {
                return daletouForecasts.size();
            }
        };
        myJdbcTemplate.batchUpdate(INSERT_DALETOUFORECAST_SQL, batchPreparedStatementSetter);
    }

    /**
     * 根据ids查询大乐透预测数据
     *
     * @param daletouId 大乐透Id
     * @param ids       ids
     * @return 大乐透预测数据列表
     */
    public List<DaletouForecast> queryByIds(Integer daletouId, List<Integer> ids) {
        String sql = "select * from " + BASE_TABLE + daletouId + " where id in " + SqlUtils.sqlInConditionLoad(ids);
        return myJdbcTemplate.query(sql, ids.toArray(), rowMapper);
    }
}
