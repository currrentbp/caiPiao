package com.bp.shuangseqiu.service;


import com.bp.constant.common.Color;
import com.bp.shuangseqiu.ShuangseqiuEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author current_bp
 * @createTime 20170916
 */
public class ShuangseqiuServiceImpl {

    /*
    2、分析数据：查询是否存在重复的数据（红球和篮球一样），分析
    3、随机最佳的数据
    4、校验最佳数据的实用性
     */

    /**
     * 获取重复数据的集合
     *
     * @param num                 期待的颗粒度
     * @param shuangseqiuEntities 给定的列表
     * @return 重复数据的集合map《id，map《颜色，map《数字，数量》》》
     */
    public Map<Integer, Map<String, Map<Integer, Integer>>> getRepeatProblem(int num, List<ShuangseqiuEntity> shuangseqiuEntities) {
        Map<Integer, Map<String, Map<Integer, Integer>>> result = new HashMap<Integer, Map<String, Map<Integer, Integer>>>();

        ConcurrentLinkedQueue<ShuangseqiuEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        for (int i = 0; i < shuangseqiuEntities.size(); i++) {
            if (concurrentLinkedQueue.size() < num) {//数据不足时，填充数据
                concurrentLinkedQueue.add(shuangseqiuEntities.get(i));
            } else {//数据充足时，分析数据
                //获取前N个数字的重复度
                Map<String, Map<Integer, Integer>> repeatMap = getRepeatMap(concurrentLinkedQueue);
                result.put(shuangseqiuEntities.get(i).getId(), repeatMap);
                concurrentLinkedQueue.remove();
                concurrentLinkedQueue.add(shuangseqiuEntities.get(i));
            }
        }

        return result;
    }


    /**
     * 获取id对应的前N个数据的集合
     *
     * @param concurrentLinkedQueue 前N个数据的列表
     * @return 整合后的数据
     */
    private Map<String, Map<Integer, Integer>> getRepeatMap(ConcurrentLinkedQueue<ShuangseqiuEntity> concurrentLinkedQueue) {
        Map<String, Map<Integer, Integer>> result = new HashMap<String, Map<Integer, Integer>>();

        result.put(Color.RED, new HashMap<Integer, Integer>());
        result.put(Color.BLUE, new HashMap<Integer, Integer>());

        Map<Integer, Integer> redMap = result.get(Color.RED);
        Map<Integer, Integer> blueMap = result.get(Color.BLUE);
        for (ShuangseqiuEntity shuangseqiuEntity : concurrentLinkedQueue) {
            for (Integer red : shuangseqiuEntity.getRed()) {
                if (redMap.containsKey(red)) {
                    redMap.put(red, redMap.get(red) + 1);
                } else {
                    redMap.put(red, 1);
                }
            }

            Integer blue = shuangseqiuEntity.getBlue();
            if (blueMap.containsKey(blue)) {
                blueMap.put(blue, blueMap.get(blue) + 1);
            } else {
                blueMap.put(blue, 1);
            }
        }

        return result;
    }


}
