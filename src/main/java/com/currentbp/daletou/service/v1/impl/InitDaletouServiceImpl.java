package com.currentbp.daletou.service.v1.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v1.InitDaletouService;
import com.currentbp.util.all.CollectionCommonUtil;
import com.currentbp.util.all.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

@Service("initDaletouService")
public class InitDaletouServiceImpl implements InitDaletouService {
    private final static Logger logger = LoggerFactory.getLogger(InitDaletouServiceImpl.class);

    @Autowired
    private DaletouDao daletouDao;

    /**
     * 从本地文本中获取大乐透历史数据
     *
     * @return 大乐透历史数据
     */
    @Override
    public List<DaletouBo> getDaletouHistoryFromLocal() {
        //TODO not test
        String local = "/daletou/daletou_history.txt";
        List<String> historyDaletous = StreamUtil.getListByFileSource(local);

        List<DaletouBo> daletouBoEntities = getDaletouListFromStringList(historyDaletous);
        List<DaletouBo> sortedDaletouBoEntities = getSortedDaletouList(daletouBoEntities);

        return sortedDaletouBoEntities;
    }

    @Override
    public List<DaletouBo> getDaletouHistoryFromRepository() {
        List<Daletou> daletous = daletouDao.queryAll();
        List<DaletouBo> result = new ArrayList<>();
        for (Daletou daletou : daletous) {
            DaletouBo daletouBo = new DaletouBo(daletou);
            result.add(daletouBo);
        }

        return getSortedDaletouList(result);
    }

    //=======     私有方法          =================================//

    /**
     * 获取大乐透列表，通过list《string》
     *
     * @param historyDaletous 列表
     * @return 大乐透列表
     */
    private List<DaletouBo> getDaletouListFromStringList(List<String> historyDaletous) {

        List<DaletouBo> result = new ArrayList<DaletouBo>();
        for (String historyDaletou : historyDaletous) {
            result.add(new DaletouBo(historyDaletou));
        }
        return result;
    }

    /**
     * 获取排序好的大乐透列表
     *
     * @param daletouBoEntities 大乐透列表
     * @return 大乐透列表
     */
    private List<DaletouBo> getSortedDaletouList(List<DaletouBo> daletouBoEntities) {
        if(CollectionUtils.isEmpty(daletouBoEntities)){
            return new ArrayList<>();
        }
        Object[] beforeSort = daletouBoEntities.toArray();
        Arrays.sort(beforeSort, new Comparator<Object>() {
            @Override
            public int compare(Object o, Object t1) {
                DaletouBo a = (DaletouBo) o;
                DaletouBo b = (DaletouBo) t1;
                if (a.getId() > b.getId()) {
                    return -1;
                } else if (a.getId() < b.getId()) {
                    return 1;
                }
                return 0;
            }
        });
        return CollectionCommonUtil.asList(beforeSort,DaletouBo.class);
    }

}
