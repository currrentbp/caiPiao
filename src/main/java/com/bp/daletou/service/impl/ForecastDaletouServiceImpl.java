package com.bp.daletou.service.impl;

import com.bp.common.entity.DaletouEntity;
import com.bp.common.entity.ProblemDate;
import com.bp.daletou.service.ForecastDaletouService;
import com.currentbp.util.all.Assert;
import com.currentbp.util.all.CollectionUtil;

import java.util.*;

/**
 * 预测大乐透
 *
 * @author current_bp
 * @createTime 20180222
 */
public class ForecastDaletouServiceImpl implements ForecastDaletouService {
    /**
     * 预测指定期号的大乐透
     *
     * @param daletouId    指定的大乐透期号
     * @param problemDates 历史概率
     * @return 预测的大乐透
     */
    public DaletouEntity forecastDaletou(int daletouId, List<ProblemDate> problemDates) {
        Map<Integer, ProblemDate> problemDateMap = CollectionUtil.getMapFromListByMethodName(problemDates, "getDaletouId");
        DaletouEntity daletouEntity = new DaletouEntity();
        daletouEntity.setId(daletouId);
        //todo not work


        return null;
    }


    /**
     * 预测指定期号的大乐透列表
     *
     * @param num          列表长度
     * @param daletouId    指定的大乐透期号
     * @param problemDates 历史概率
     * @return 预测的大乐透
     */
    public List<DaletouEntity> forecastDaletou(int num, int daletouId, List<ProblemDate> problemDates) {
        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        for (int i = 0; i < num; i++) {
            DaletouEntity daletouEntity = forecastDaletou(daletouId, problemDates);
            result.add(daletouEntity);
        }
        return result;
    }

    /**
     * 获取大乐透的历史概率列表：降序
     *
     * @param problemDates 重复概率列表
     * @return 降序的重复概率列表
     */
    private List<ProblemDate> getDescSortedProblemDateList(List<ProblemDate> problemDates) {
        Object[] beforeSort = problemDates.toArray();
        Arrays.sort(beforeSort, new Comparator<Object>() {
            public int compare(Object o, Object t1) {
                ProblemDate a = (ProblemDate) o;
                ProblemDate b = (ProblemDate) t1;
                if (a.getDaletouId() > b.getDaletouId()) {
                    return 1;
                } else if (a.getDaletouId() < b.getDaletouId()) {
                    return -1;
                }
                return 0;
            }
        });
        return CollectionUtil.asList(beforeSort, ProblemDate.class);
    }
}
