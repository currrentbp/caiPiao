package com.currentbp.daletou.service.common;

import com.currentbp.daletou.bo.entity.DaletouBo;

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
    List<DaletouBo> downLoadNewDaletouHistory(Integer startDaletouId);

    /**
     * 下载当个大乐透
     *
     * @param daletouId 大乐透ID
     * @return 大乐透
     */
    DaletouBo downLoadDaletouHistory(Integer daletouId);

    /**
     * 下载所有的大乐透
     *
     * @return 大乐透列表
     */
    List<DaletouBo> downLoadAllDaletouHistory();
}
