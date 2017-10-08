package com.bp.shuangseqiu.service;

import com.alibaba.fastjson.JSON;
import com.bp.shuangseqiu.ShuangseqiuEntity;
import com.bp.util.all.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 策略
 *
 * @author current_bp
 * @createTime 20171006
 */
public class StrategyServiceImpl {
    private final static Logger logger = LoggerFactory.getLogger(StrategyServiceImpl.class);

    /**
     * 平均数方法获取双色球
     *
     * @param num 获取双色球的数量
     * @return
     */
    public List<ShuangseqiuEntity> averageStrategy(int num,Integer id, List<ShuangseqiuEntity> localShuangseqiuEntities) {
        if(CheckUtil.isEmpty(localShuangseqiuEntities)){
            localShuangseqiuEntities = new LoadShuangseqiu().loadAllShuangseqiuFromLocal(null);
        }

        Map<Integer, Map<String, Map<Integer, Integer>>> repeatNums = new ShuangseqiuServiceImpl().getRepeatNums(num, localShuangseqiuEntities);

        Map<Integer, Map<String, Integer>> totalRepeatProblem = new ShuangseqiuServiceImpl()
                .getTotalRepeatProblem(num, localShuangseqiuEntities, repeatNums);



        return null;
    }

}
