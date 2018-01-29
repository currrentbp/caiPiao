package com.bp.shuangseqiu.service;


import com.bp.common.constant.Color;
import com.bp.shuangseqiu.ShuangseqiuEntity;

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
    public Map<Integer, Map<String, Map<Integer, Integer>>> getRepeatNums(int num, List<ShuangseqiuEntity> shuangseqiuEntities) {
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
     * 获取历史数据的重复的数量
     *
     * @param num
     * @param shuangseqiuEntities
     * @param repeatSource
     * @return 历史数据的重复数量：《id，《颜色，重复数量》》
     */
    public Map<Integer, Map<String, Integer>> getTotalRepeatProblem(int num, List<ShuangseqiuEntity> shuangseqiuEntities,
                                                                    Map<Integer, Map<String, Map<Integer, Integer>>> repeatSource) {
        Map<Integer, Map<String, Integer>> result = new HashMap<Integer, Map<String, Integer>>();
        boolean start = false;
        ShuangseqiuEntity before = null;
        for (ShuangseqiuEntity shuangseqiuEntity : shuangseqiuEntities) {
            if (!start) {//由于有些数据是无效的，例如前面N个数据是历史分析数据，去除这些数据
                if (repeatSource.containsKey(shuangseqiuEntity.getId())) {
                    start = true;
                    before = shuangseqiuEntity;
                    continue;//此处直接跳过的原因：从下一个数据开始做分析
                } else {
                    continue;
                }
            }

            //上一期对应的前N期的历史数据综合
            Map<String, Map<Integer, Integer>> repeat = repeatSource.get(before.getId());
            //红色：本期与之前的重复数量
            Map<Integer, Integer> repeatReds = repeat.get(Color.RED);
            int redRepeatCount = 0;
            for (Integer red : shuangseqiuEntity.getRed()) {
                if (repeatReds.containsKey(red)) {
                    redRepeatCount += repeatReds.get(red);
                }
            }
            //蓝色：本期与之前的重复数量
            Map<Integer, Integer> repeatBlue = repeat.get(Color.BLUE);
            int blueRepeatCount = 0;
            if (repeatBlue.containsKey(shuangseqiuEntity.getBlue())) {
                blueRepeatCount += repeatBlue.get(shuangseqiuEntity.getBlue());
            }

            Map<String, Integer> repeatCount = new HashMap<String, Integer>();
            repeatCount.put(Color.RED, redRepeatCount);
            repeatCount.put(Color.BLUE, blueRepeatCount);

            result.put(shuangseqiuEntity.getId(), repeatCount);
        }
        return result;
    }


    //=========        私有方法           ======================================//

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
