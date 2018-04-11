package com.currentbp.daletou.service;

import com.currentbp.entity.DaletouEntity;

import java.util.List;

/**
 * 将内容写入指定的路径下
 *
 * @author current_bp
 * @createTime 20180311
 */
public interface WriterService {

    /**
     * 将大乐透的历史数据追加写入历史文件中
     *
     * @param daletouEntities 大乐透列表
     */
    void writeDaletouHistory2Local(List<DaletouEntity> daletouEntities);
}
