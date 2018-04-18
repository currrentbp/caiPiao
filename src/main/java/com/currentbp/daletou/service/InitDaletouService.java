package com.currentbp.daletou.service;

import com.currentbp.daletou.bo.entity.DaletouBo;

import java.util.List;

/**
 * 初始化模块
 *
 * @author current_bp
 * @createTime 20180124
 */
public interface InitDaletouService {

    /*
     *1、从本地文件中获取历史数据，从小到大排序，即：第一个是大乐透的最前面的数据
     */

    /**
     * 从本地文本中获取大乐透历史数据
     *
     * @return 大乐透历史数据
     */
    List<DaletouBo> getDaletouHistoryFromLocal();
}
