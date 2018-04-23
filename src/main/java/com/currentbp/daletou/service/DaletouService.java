package com.currentbp.daletou.service;

import com.currentbp.daletou.condition.DaletouCondition;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.vo.Win;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180418
 */
public interface DaletouService {

    /**
     * 插入一个大乐透
     *
     * @param daletou 大乐透
     */
    void insert(Daletou daletou);

    /**
     * 查询所有的大乐透
     *
     * @return 大乐透列表
     */
    List<Daletou> queryDaletouAll();

    /**
     * 根据条件查询大乐透
     *
     * @param daletouCondition 条件
     * @return 大乐透列表
     */
    List<Daletou> queryDaletouByCondition(DaletouCondition daletouCondition);

    /**
     * 检查中奖情况
     *
     * @param daletous 大乐透列表
     * @return 中奖情况
     */
    List<Win> isWin(List<Daletou> daletous);
}
