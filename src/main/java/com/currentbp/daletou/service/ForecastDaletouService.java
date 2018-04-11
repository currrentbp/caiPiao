package com.currentbp.daletou.service;

import com.currentbp.entity.DaletouEntity;
import com.currentbp.entity.HistoryDate;
import com.currentbp.entity.ProblemDate;

import java.util.List;

/**
 * 预测大乐透
 *
 * @author current_bp
 * @createTime 20180222
 */
public interface ForecastDaletouService {

    /**
     * 预测指定期号的大乐透
     *
     * @param num            获取样本的数量
     * @param daletouId      指定的大乐透期号
     * @param problemDates   历史概率
     * @param historyRepeats 历史重复数
     * @return 预测的大乐透
     */
    DaletouEntity forecastDaletou(int num, int daletouId, List<ProblemDate> problemDates, List<HistoryDate> historyRepeats);

    /**
     * 预测指定期号的大乐透列表
     *
     * @param count          大乐透的数量
     * @param num            获取样本的数量
     * @param daletouId      指定的大乐透期号
     * @param problemDates   历史概率
     * @param historyRepeats 历史重复数
     * @return 预测的大乐透
     */
    List<DaletouEntity> forecastDaletou(int count, int num, int daletouId, List<ProblemDate> problemDates, List<HistoryDate> historyRepeats);
}
