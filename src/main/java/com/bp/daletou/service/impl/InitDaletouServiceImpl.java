package com.bp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.bp.daletou.entity.DaletouEntity;
import com.bp.daletou.service.DaletouServiceImpl;
import com.bp.daletou.service.InitDaletouService;
import com.currentbp.util.all.CollectionUtil;
import com.currentbp.util.all.StreamUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * 初始化模块:实现
 *
 * @author current_bp
 * @createTime 20180124
 */

@Service
public class InitDaletouServiceImpl implements InitDaletouService {
    private final static Logger logger = LoggerFactory.getLogger(InitDaletouServiceImpl.class);

    /**
     * 从本地文本中获取大乐透历史数据
     *
     * @return 大乐透历史数据
     */
    public List<DaletouEntity> getDaletouHistoryFromLocal() {
        //TODO not test
        String local = "/daletou/daletou_history.txt";
        List<String> historyDaletous = StreamUtil.getListByFileSource(local);

        List<DaletouEntity> daletouEntities = getDaletouListFromStringList(historyDaletous);
        List<DaletouEntity> sortedDaletouEntities = getSortedDaletouList(daletouEntities);

        return sortedDaletouEntities;
    }


    //=======     私有方法          =================================//

    /**
     * 获取大乐透列表，通过list《string》
     *
     * @param historyDaletous 列表
     * @return 大乐透列表
     */
    private List<DaletouEntity> getDaletouListFromStringList(List<String> historyDaletous) {

        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        for (String historyDaletou : historyDaletous) {
            result.add(new DaletouEntity(historyDaletou));
        }
        return result;
    }

    /**
     * 获取排序好的大乐透列表
     *
     * @param daletouEntities 大乐透列表
     * @return 大乐透列表
     */
    private List<DaletouEntity> getSortedDaletouList(List<DaletouEntity> daletouEntities) {
        Object[] beforeSort = daletouEntities.toArray();
        Arrays.sort(beforeSort, new Comparator<Object>() {
            public int compare(Object o, Object t1) {
                DaletouEntity a = (DaletouEntity) o;
                DaletouEntity b = (DaletouEntity) t1;
                if (a.getId() > b.getId()) {
                    return -1;
                } else if (a.getId() < b.getId()) {
                    return 1;
                }
                return 0;
            }
        });
        return CollectionUtil.asList(beforeSort,DaletouEntity.class);
    }

}
