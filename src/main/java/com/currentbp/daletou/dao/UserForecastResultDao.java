package com.currentbp.daletou.dao;

import com.currentbp.daletou.entity.UserDaletou;
import com.currentbp.daletou.entity.UserForecastResult;
import com.currentbp.jdbc.MyJdbcTemplate;
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
import java.util.Date;
import java.util.List;

/**
 * @author baopan
 * @createTime 20210413
 */
@Repository
public class UserForecastResultDao {
    private static RowMapper<UserForecastResult> rowMapper = new BeanPropertyRowMapper(UserForecastResult.class);
    @Resource
    private MyJdbcTemplate myJdbcTemplate;

    private static final String INSERT_SQL = "insert into user_daletou" +
            "(user_forecast_id,caipiao_type,win,bonus,win_level,create_time,update_time) " +
            "values(?,?,?,?,?,?,?)";

    public void insert(UserForecastResult userForecastResult) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        myJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                Date now = new Date();
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setObject(1, userForecastResult.getUserForecastId());
                ps.setObject(2, userForecastResult.getCaipiaoType());
                ps.setObject(3, userForecastResult.getWin());
                ps.setObject(4, userForecastResult.getBonus());
                ps.setObject(5, userForecastResult.getWinLevel());
                ps.setObject(6, now);
                ps.setObject(7, now);
                return ps;
            }
        }, keyHolder);
    }


}
