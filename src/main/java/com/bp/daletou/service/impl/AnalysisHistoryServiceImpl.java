package com.bp.daletou.service.impl;


import com.bp.common.entity.HistoryDate;
import com.bp.common.entity.DaletouEntity;
import com.bp.common.entity.ProblemDate;
import com.bp.daletou.service.AnalysisHistoryService;
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
     * @param daletouEntities 历史数据
     * @return 重复的数据
     */
    public List<HistoryDate> getHistoryRepeatsFromHistory(int num, List<DaletouEntity> daletouEntities) {
        Assert.notEmpty(daletouEntities, "历史数据为空");
        Assert.isTrue(num > 0, "num 必须是正整数");

        List<DaletouEntity> daletouEntities1 = getDescSortedDaletouList(daletouEntities);
        LinkedBlockingQueue<DaletouEntity> linkedBlockingQueue = new LinkedBlockingQueue<DaletouEntity>();
        List<HistoryDate> result = new ArrayList<HistoryDate>();

        DaletouEntity currentDaletouEntity = null;
        for (DaletouEntity daletouEntity : daletouEntities1) {
            //如果队列中的数据少于num，就继续添加，否则就进行计算，获取重复的相关记录
            if (linkedBlockingQueue.size() < num) {

            } else {
                Iterator<DaletouEntity> iterator = linkedBlockingQueue.iterator();

                HistoryDate historyDate = new HistoryDate();
                historyDate.setId(currentDaletouEntity.getId());
                while (iterator.hasNext()) {
                    DaletouEntity next = iterator.next();
                    historyDate.addReds(next.getRed());
                    historyDate.addBlues(next.getBlue());
                }
                result.add(historyDate);
                //从头部拿出一个元素
                linkedBlockingQueue.poll();
            }
            //添加一个元素
            linkedBlockingQueue.offer(daletouEntity);
            currentDaletouEntity = daletouEntity;
        }

        return result;
    }

    /**
     * 从历史重复数据中获取每期大乐透与前N期的重复率
     *
     * @param daletouEntities 大乐透列表
     * @param historyDates    历史重复数据
     * @return
     */
    public List<ProblemDate> getHistoryProblemDatesFromHistory(List<DaletouEntity> daletouEntities,
                                                               List<HistoryDate> historyDates) {
        Map<Object, HistoryDate> historyDateMap = CollectionCommonUtil.getMapFromListByMethodName(historyDates, "getId");
        List<ProblemDate> problemDates = new ArrayList<ProblemDate>();
        for (DaletouEntity daletouEntity : daletouEntities) {
            HistoryDate historyDate = historyDateMap.get(daletouEntity.getId() - 1);
            if (null != historyDate) {
                ProblemDate problemDate = doGetHistoryProblemDateFromHistory(daletouEntity, historyDate);
                problemDates.add(problemDate);
            }
        }
        return problemDates;
    }


    //=====      private       ================================//

    /**
     * 根据大乐透的一个对象和大乐透历史数据获取一个大乐透的重复率
     *
     * @param daletouEntity 大乐透
     * @param historyDate   大乐透历史数据
     * @return 重复率
     */
    private ProblemDate doGetHistoryProblemDateFromHistory(DaletouEntity daletouEntity, HistoryDate historyDate) {

        ProblemDate problemDate = new ProblemDate();
        problemDate.setDaletouId(daletouEntity.getId());
        problemDate.setBlueRepeats(new ArrayList<Integer>());
        problemDate.setRedRepeats(new ArrayList<Integer>());
        int redRepeat = 0;
        for (Integer red : historyDate.getReds()) {
            if (daletouEntity.getRed().contains(red)) {
                redRepeat++;
                problemDate.getRedRepeats().add(red);
            }
        }
        problemDate.setRedProblem(1.0F * redRepeat / historyDate.getReds().size());
        int blueRepeat = 0;
        for (Integer blue : historyDate.getBlues()) {
            if (daletouEntity.getBlue().contains(blue)) {
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
     * @param daletouEntities 大乐透列表
     * @return 倒序列表
     */
    private List<DaletouEntity> getDescSortedDaletouList(List<DaletouEntity> daletouEntities) {
        Object[] beforeSort = daletouEntities.toArray();
        Arrays.sort(beforeSort, new Comparator<Object>() {
            public int compare(Object o, Object t1) {
                DaletouEntity a = (DaletouEntity) o;
                DaletouEntity b = (DaletouEntity) t1;
                if (a.getId() > b.getId()) {
                    return 1;
                } else if (a.getId() < b.getId()) {
                    return -1;
                }
                return 0;
            }
        });
        return CollectionCommonUtil.asList(beforeSort, DaletouEntity.class);
    }
}
