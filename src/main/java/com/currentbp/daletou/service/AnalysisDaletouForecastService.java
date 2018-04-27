package com.currentbp.daletou.service;

import com.currentbp.daletou.bo.entity.AnalysisDaletouForecast;

/**
 * 分析已经预测的大乐透
 *
 * @author current_bp
 * @createTime 20180427
 */
public interface AnalysisDaletouForecastService {

    /**
     * 分析大乐透的预测结果
     *
     * @param daletouId 大乐透ID
     * @return 预测结果
     */
    AnalysisDaletouForecast analysisDaletouForecast(Integer daletouId);

}
