package com.bp.daletou.service;


import com.bp.common.entity.HistoryDate;
import com.bp.common.entity.DaletouEntity;

import java.util.List;

/**
 * 分析历史数据
 *
 * @author current_bp
 * @createTime 20180128
 */
public interface AnalysisHistoryService {

    /*
    {
        daletouID:表示前N个历史数据的重复数据，包括当前的ID,
                  {
                    {reds,[1,2,4]},
                    {blues,[1,2]}
                  }
    }
     */

    /**
     * 从历史数据中获取重复的相关记录
     *
     * @param num             取样本的数量
     * @param daletouEntities 历史数据
     * @return 重复的数据
     */
    List<HistoryDate> getHistoryRepeatsFromHistory(int num, List<DaletouEntity> daletouEntities);

}
