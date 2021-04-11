package com.currentbp.daletou.service.v2;

import com.currentbp.daletou.entity.Daletou;

import java.util.List;

/**
 * @author baopan
 * @createTime 20210411
 */
public interface DaletouServiceVTwo {
    /**
     * 预测大乐透：第二版
     */
    List<Daletou> forecastV2(int count,List<Daletou> oldDaletous);
}
