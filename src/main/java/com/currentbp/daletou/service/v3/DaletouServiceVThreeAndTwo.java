package com.currentbp.daletou.service.v3;

import com.currentbp.daletou.entity.Daletou;

import java.util.List;

public interface DaletouServiceVThreeAndTwo {
    /**
     * 大乐透第三种策略预测&第二种策略
     * @param daletouId 大乐透id
     * @param count 数量
     * @return
     */
    List<Daletou> forecastV3_2(int daletouId, int count,List<Daletou> oldDaletous);
}
