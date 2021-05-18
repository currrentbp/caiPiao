package com.currentbp.daletou.dao;


import com.currentbp.daletou.condition.DaletouPageCondition;
import com.currentbp.daletou.entity.Daletou;
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
import java.util.List;

/**
 * 大乐透dao
 *
 * @author curren_bp
 * @createTime 20180418
 */
@Repository
public class DaletouDao {

    private static final String BASE_COLUMN = " id,red1,red2,red3,red4,red5,blue1,blue2 ";
    private static final String INSERT_GOODS_SQL = "insert into daletou(id,red1,red2,red3,red4,red5,blue1,blue2) values(?,?,?,?,?,?,?,?)";

    private static RowMapper<Daletou> rowMapper = new BeanPropertyRowMapper(Daletou.class);

    @Resource
    private MyJdbcTemplate myJdbcTemplate;


    /**
     * 插入一个大乐透
     *
     * @param daletou 大乐透
     */
    public void insert(Daletou daletou) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        myJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_GOODS_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setObject(1, daletou.getId());
                ps.setObject(2, daletou.getRed1());
                ps.setObject(3, daletou.getRed2());
                ps.setObject(4, daletou.getRed3());
                ps.setObject(5, daletou.getRed4());
                ps.setObject(6, daletou.getRed5());
                ps.setObject(7, daletou.getBlue1());
                ps.setObject(8, daletou.getBlue2());
                return ps;
            }
        }, keyHolder);
    }

    /**
     * 查询出所有的大乐透
     *
     * @return 大乐透列表
     */
    public List<Daletou> queryAll() {
        final String sql = "select * from daletou order by id desc";
        return myJdbcTemplate.query(sql, rowMapper);
    }


    /**
     * 根据条件查询大乐透列表
     *
     * @param daletouPageCondition 条件
     * @return 列表
     */
    public List<Daletou> queryByCondition(DaletouPageCondition daletouPageCondition) {
        StringBuilder sql = new StringBuilder("select * from daletou ");
        sql.append(" where 1=1 ");
        List<Object> parms = new ArrayList<>();

        sql.append(" order by id desc ");

        sql.append(" limit ?,?");
        parms.add((daletouPageCondition.getPageNum() - 1) * daletouPageCondition.getPageSize());
        parms.add(daletouPageCondition.getPageSize());
        return myJdbcTemplate.query(sql.toString(), parms.toArray(), rowMapper);
    }

    /**
     * 根据ids查询列表
     *
     * @param ids ids
     * @return 大乐透列表
     */
    public List<Daletou> queryByIds(List<Integer> ids) {
        StringBuilder sql = new StringBuilder("select * from daletou ");
        sql.append(" where id in ").append(SqlUtils.sqlInConditionLoad(ids));

        return myJdbcTemplate.query(sql.toString(), ids.toArray(), rowMapper);
    }

    /**
     * 根据ids查询列表
     *
     * @param id id
     * @return 大乐透
     */
    public Daletou queryById(Integer id) {
        StringBuilder sql = new StringBuilder("select * from daletou ");
        sql.append(" where id =? ");

        return myJdbcTemplate.queryForObject(sql.toString(), Lists.newArrayList(id).toArray(), rowMapper);
    }

    public Integer queryMaxIdFromMinIdAndMaxId(int minId, int maxId) {
        String sql = "select max(id) as 'id' from daletou where id>=? and id<=? ";
        Daletou daletou = myJdbcTemplate.queryForObject(sql, Lists.newArrayList(minId, maxId).toArray(), rowMapper);
        return null == daletou ? null : daletou.getId();
    }

    public Integer queryMaxId() {
        String sql = "select max(id) as 'id' from daletou ";
        Daletou daletou = myJdbcTemplate.queryForObject(sql, rowMapper);
        return null == daletou ? null : daletou.getId();
    }

    public boolean queryByRedAndBlue(Daletou daletou) {
        String sql = "select * from daletou where red1=? and red2=? and red3=? and red4=? and red5=? and blue1=? and blue2=? ";
        Daletou daletou1 = myJdbcTemplate.queryForObject(sql, Lists.newArrayList(daletou.getRed1(),daletou.getRed2(),
                daletou.getRed3(),daletou.getRed4(),daletou.getRed5(),daletou.getBlue1(),daletou.getBlue2()).toArray(), rowMapper);
        return null != daletou1;
    }
}
