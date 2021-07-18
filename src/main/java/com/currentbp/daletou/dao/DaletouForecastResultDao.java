package com.currentbp.daletou.dao;


import com.currentbp.daletou.condition.DaletouPageCondition;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.entity.DaletouForecastResult;
import com.currentbp.jdbc.MyJdbcTemplate;
import com.currentbp.util.SqlUtils;
import org.assertj.core.util.Lists;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 大乐透预测结果dao
 *
 * @author curren_bp
 * @createTime 20180418
 */
@Repository
public class DaletouForecastResultDao {

    private static final String BASE_COLUMN = " id,daletou_id,daletou,forecast_version,used,create_time,update_time ";
    private static final String INSERT_GOODS_SQL = "insert into daletou_forecast_result(daletou_id,daletou,forecast_version,used,create_time,update_time) values(?,?,?,?,?,?)";



    @Resource
    private MyJdbcTemplate myJdbcTemplate;


    /**
     * 插入一个大乐透
     *
     * @param daletou 大乐透
     */
    public void insert(DaletouForecastResult daletou) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        myJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_GOODS_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setObject(1, daletou.getDaletouId());
                ps.setObject(2, daletou.getDaletou());
                ps.setObject(3, daletou.getDaletouVersion());
                ps.setObject(4, daletou.getUsed());
                ps.setObject(5, new Date());
                ps.setObject(6, new Date());
                return ps;
            }
        }, keyHolder);
    }


}
