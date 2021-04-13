package com.currentbp.daletou.dao;

import com.currentbp.daletou.entity.UserDaletou;
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
 * @createTime 20210412
 */
@Repository
public class UserDaletouDao {
    private static RowMapper<UserDaletou> rowMapper = new BeanPropertyRowMapper(UserDaletou.class);
    @Resource
    private MyJdbcTemplate myJdbcTemplate;

    private static final String INSERT_SQL = "insert into user_daletou" +
            "(user_id,forecast_version,daletou,create_time,update_time,daletou_id,win) " +
            "values(?,?,?,?,?,?,?)";

    public void insert(UserDaletou userDaletou) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        myJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                Date now = new Date();
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setObject(1, userDaletou.getUserId());
                ps.setObject(2, userDaletou.getForecastVersion());
                ps.setObject(3, userDaletou.getDaletou());
                ps.setObject(4, now);
                ps.setObject(5, now);
                ps.setObject(6, userDaletou.getDaletouId());
                ps.setObject(7, 0);//默认初始化状态
                return ps;
            }
        }, keyHolder);
    }

    public List<UserDaletou> queryByDaletouId(Integer daletouId) {
        final String sql = "select * from user_daletou order by id desc";
        return myJdbcTemplate.query(sql, rowMapper);
    }

    public void update(Long id, boolean win) {
        final String sql = "update user_daletou set win = ?,update_time = ? where id = ?";
        myJdbcTemplate.update(sql, new Object[]{win ? 1 : 2, new Date(), id});
    }
}
