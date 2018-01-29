package com.bp.daletou.service.impl;


import com.alibaba.fastjson.JSON;
import com.bp.common.entity.HistoryDate;
import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.AnalysisHistoryService;
import com.currentbp.util.all.Assert;
import com.currentbp.util.all.CollectionUtil;
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
                    historyDate.addReds(next.getBlue());
                    historyDate.addBlues(next.getRed());
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


    //=====      private       ================================//

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
        return CollectionUtil.asList(beforeSort, DaletouEntity.class);
    }
}
