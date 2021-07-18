package com.currentbp.daletou.service.v3;

import com.currentbp.daletou.entity.Daletou;

import java.util.List;

public interface DaletouServiceVThree {
    /**
     * 大乐透第三种策略预测
     * @param daletouId 大乐透id
     * @param count 数量
     * @return
     */
    List<Daletou> forecastV3(int daletouId, int count);

    /**
     * 保存所有的预测
     */
    void saveForecastV3(long daletouId);
}
