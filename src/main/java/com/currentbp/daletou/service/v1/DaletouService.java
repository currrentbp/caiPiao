package com.currentbp.daletou.service.v1;

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
     * 根据大乐透ID查询大乐透
     *
     * @param daletouId 大乐透ID
     * @return 大乐透
     */
    Daletou queryById(Integer daletouId);

    /**
     * 根据条件查询大乐透
     *
     * @param daletouCondition 条件
     * @return 大乐透列表
     */
    List<Daletou> queryDaletouByCondition(DaletouCondition daletouCondition);

    /**
     * 检查中奖情况，只能检查同一期的大乐透
     *
     * @param daletous 大乐透列表
     * @return 中奖情况
     */
    List<Win> isWin(List<Daletou> daletous);

    /**
     * 预测大乐透
     *
     * @param num       预测数量
     * @param daletouId 大乐透期号
     * @return 大乐透列表
     */
    List<Daletou> forecast(int num, int daletouId);

    /**
     * 预测大乐透并保存
     *
     * @param daletouId 最新大乐透
     */
    void forecastAndSave(int daletouId);

    /**
     * 判断是否中奖，以及中奖情况
     *
     * @param daletou 需要预测的大乐透
     * @param source  大乐透
     * @return 是否中奖
     */
    Win isWin(Daletou daletou, Daletou source);

}
