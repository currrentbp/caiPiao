package com.currentbp.daletou.service;

import com.currentbp.daletou.entity.DaletouForecast;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180426
 */
public interface DaletouForecastService {

    /**
     * 创建表
     *
     * @param daletouId 大乐透Id
     */
    void createTable(Integer daletouId);

    /**
     * 批量插入大乐透预测数据
     *
     * @param daletouId        大乐透Id
     * @param daletouForecasts 预测列表
     */
    void batchInsert(Integer daletouId, List<DaletouForecast> daletouForecasts);

    /**
     * 根据IDs查询出列表
     *
     * @param daletouId
     * @param ids       ids
     * @return 大乐透预测数据列表
     */
    List<DaletouForecast> queryByIds(Integer daletouId, List<Integer> ids);
}
