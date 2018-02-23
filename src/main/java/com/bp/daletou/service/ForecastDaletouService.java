package com.bp.daletou.service;

import com.bp.common.entity.DaletouEntity;
import com.bp.common.entity.ProblemDate;

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
     * @param daletouId    指定的大乐透期号
     * @param problemDates 历史概率
     * @return 预测的大乐透
     */
    DaletouEntity forecastDaletou(int daletouId, List<ProblemDate> problemDates);

    /**
     * 预测指定期号的大乐透列表
     *
     * @param num          列表长度
     * @param daletouId    指定的大乐透期号
     * @param problemDates 历史概率
     * @return 预测的大乐透
     */
    List<DaletouEntity> forecastDaletou(int num, int daletouId, List<ProblemDate> problemDates);
}
