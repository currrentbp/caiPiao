package com.currentbp.daletou.service.v1.impl;


import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.HistoryDate;
import com.currentbp.daletou.bo.entity.ProblemDate;
import com.currentbp.daletou.service.v1.AnalysisHistoryService;
import com.currentbp.util.all.Assert;
import com.currentbp.util.all.CollectionCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 分析历史数据
 *
 * @author current_bp
 * @createTime 20180128
 */
@Service("analysisHistoryService")
public class AnalysisHistoryServiceImpl implements AnalysisHistoryService {
    private final static Logger logger = LoggerFactory.getLogger(AnalysisHistoryServiceImpl.class);

    /**
     * 从历史数据中获取重复的相关记录
     *
     * @param num             取样本的数量
     * @param daletouBoEntities 历史数据
     * @return 重复的数据
     */
    @Override
    public List<HistoryDate> getHistoryRepeatsFromHistory(int num, List<DaletouBo> daletouBoEntities) {
        Assert.notEmpty(daletouBoEntities, "历史数据为空");
        Assert.isTrue(num > 0, "num 必须是正整数");

        List<DaletouBo> daletouBoEntities1 = getDescSortedDaletouList(daletouBoEntities);
        LinkedBlockingQueue<DaletouBo> linkedBlockingQueue = new LinkedBlockingQueue<DaletouBo>();
        List<HistoryDate> result = new ArrayList<HistoryDate>();

        DaletouBo currentDaletouBo = null;
        for (DaletouBo daletouBo : daletouBoEntities1) {
            //如果队列中的数据少于num，就继续添加，否则就进行计算，获取重复的相关记录
            if (linkedBlockingQueue.size() < num) {

            } else {
                Iterator<DaletouBo> iterator = linkedBlockingQueue.iterator();

                HistoryDate historyDate = new HistoryDate();
                historyDate.setId(currentDaletouBo.getId());
                while (iterator.hasNext()) {
                    DaletouBo next = iterator.next();
                    historyDate.addReds(next.getRed());
                    historyDate.addBlues(next.getBlue());
                }
                result.add(historyDate);
                //从头部拿出一个元素
                linkedBlockingQueue.poll();
            }
            //添加一个元素
            linkedBlockingQueue.offer(daletouBo);
            currentDaletouBo = daletouBo;
        }

        return result;
    }

    /**
     * 从历史重复数据中获取每期大乐透与前N期的重复率
     *
     * @param daletouBoEntities 大乐透列表
     * @param historyDates    历史重复数据
     * @return
     */
    @Override
    public List<ProblemDate> getHistoryProblemDatesFromHistory(List<DaletouBo> daletouBoEntities,
                                                               List<HistoryDate> historyDates) {
        Map<Integer, HistoryDate> historyDateMap = CollectionCommonUtil.getMapFromListByMethodName(historyDates, "getId",Integer.class);
        List<ProblemDate> problemDates = new ArrayList<ProblemDate>();
        for (DaletouBo daletouBo : daletouBoEntities) {
            HistoryDate historyDate = historyDateMap.get(daletouBo.getId() - 1);
            if (null != historyDate) {
                ProblemDate problemDate = doGetHistoryProblemDateFromHistory(daletouBo, historyDate);
                problemDates.add(problemDate);
            }
        }
        return problemDates;
    }


    //=====      private       ================================//

    /**
     * 根据大乐透的一个对象和大乐透历史数据获取一个大乐透的重复率
     *
     * @param daletouBo 大乐透
     * @param historyDate   大乐透历史数据
     * @return 重复率
     */
    private ProblemDate doGetHistoryProblemDateFromHistory(DaletouBo daletouBo, HistoryDate historyDate) {

        ProblemDate problemDate = new ProblemDate();
        problemDate.setDaletouId(daletouBo.getId());
        problemDate.setBlueRepeats(new ArrayList<Integer>());
        problemDate.setRedRepeats(new ArrayList<Integer>());
        int redRepeat = 0;
        for (Integer red : historyDate.getReds()) {
            if (daletouBo.getRed().contains(red)) {
                redRepeat++;
                problemDate.getRedRepeats().add(red);
            }
        }
        problemDate.setRedProblem(1.0F * redRepeat / historyDate.getReds().size());
        int blueRepeat = 0;
        for (Integer blue : historyDate.getBlues()) {
            if (daletouBo.getBlue().contains(blue)) {
                blueRepeat++;
                problemDate.getBlueRepeats().add(blue);
            }
        }
        problemDate.setBlueProblem(1.0F * blueRepeat / historyDate.getBlues().size());
        return problemDate;
    }

    /**
     * 获取一个倒序的大乐透历史列表
     *
     * @param daletouBoEntities 大乐透列表
     * @return 倒序列表
     */
    private List<DaletouBo> getDescSortedDaletouList(List<DaletouBo> daletouBoEntities) {
        Object[] beforeSort = daletouBoEntities.toArray();
        Arrays.sort(beforeSort, new Comparator<Object>() {
            @Override
            public int compare(Object o, Object t1) {
                DaletouBo a = (DaletouBo) o;
                DaletouBo b = (DaletouBo) t1;
                if (a.getId() > b.getId()) {
                    return 1;
                } else if (a.getId() < b.getId()) {
                    return -1;
                }
                return 0;
            }
        });
        return CollectionCommonUtil.asList(beforeSort, DaletouBo.class);
    }
}
