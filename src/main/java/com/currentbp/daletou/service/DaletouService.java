package com.currentbp.daletou.service;

import com.currentbp.daletou.entity.Daletou;

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
}
