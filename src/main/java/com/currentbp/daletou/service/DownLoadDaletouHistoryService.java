package com.currentbp.daletou.service;

import com.currentbp.entity.DaletouEntity;

import java.util.List;


/**
 * 下载大乐透历史
 *
 * @author current_bp
 * @createTime 20180128
 */
public interface DownLoadDaletouHistoryService {


    /**
     * 下载最新的大乐透的历史数据
     *
     * @param startDaletouId 最开始下载的大乐透ID
     * @return 大乐透列表
     */
    List<DaletouEntity> downLoadNewDaletouHistory(Integer startDaletouId);

    /**
     * 下载当个大乐透
     *
     * @param daletouId 大乐透ID
     * @return 大乐透
     */
    DaletouEntity downLoadDaletouHistory(Integer daletouId);

    /**
     * 下载所有的大乐透
     *
     * @return 大乐透列表
     */
    List<DaletouEntity> downLoadAllDaletouHistory();
}