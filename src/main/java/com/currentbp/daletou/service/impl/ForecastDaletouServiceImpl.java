package com.currentbp.daletou.service.impl;

import static com.currentbp.common.constant.DaletouConstant.DaletouCount;
import static com.currentbp.common.constant.DaletouConstant.DaletouNumCount;

import com.currentbp.common.constant.DaletouConstant;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.HistoryDate;
import com.currentbp.daletou.bo.entity.ProblemDate;
import com.currentbp.daletou.service.ForecastDaletouService;
import com.currentbp.util.all.Assert;
import com.currentbp.util.all.CollectionCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 预测大乐透
 *
 * @author current_bp
 * @createTime 20180222
 */
@Service
public class ForecastDaletouServiceImpl implements ForecastDaletouService {
    /**
     * 预测指定期号的大乐透
     *
     * @param num            获取样本的数量
     * @param daletouId      指定的大乐透期号
     * @param problemDates   历史概率
     * @param historyRepeats 历史重复数
     * @return 预测的大乐透
     */
    public DaletouBo forecastDaletou(int num, int daletouId, List<ProblemDate> problemDates, List<HistoryDate> historyRepeats) {
        Map<Integer, ProblemDate> problemDateMap = CollectionCommonUtil.getMapFromListByMethodName(problemDates, "getDaletouId");
        DaletouBo daletouBo = new DaletouBo();
        daletouBo.setId(daletouId);
        daletouBo.setBlue(new ArrayList<Integer>());
        daletouBo.setRed(new ArrayList<Integer>());

        //1、计算平均概率
        List<ProblemDate> descSortedProblemDateList = getDescSortedProblemDateList(problemDates);
        float redProblemAvg = 0;
        float blueProblemAvg = 0;
        for (int i = 0; i < num; i++) {
            ProblemDate problemDate = descSortedProblemDateList.get(i);
            if (null == problemDate) {//由于大乐透中间是断层的，可能遇到
                num++;
                if (num > descSortedProblemDateList.size()) {
                    break;//超过列表长度，直接出来
                }
                continue;
            }
            Float redProblem = problemDate.getRedProblem();
            Float blueProblem = problemDate.getBlueProblem();
            redProblemAvg += redProblem;
            blueProblemAvg += blueProblem;
        }
        blueProblemAvg = blueProblemAvg / num;
        redProblemAvg = redProblemAvg / num;
        //2、获取平均概率下红球和篮球各中的数量
        ProblemDate nextProblemDate = getNextProblemDate(problemDateMap, daletouId - 1);
        List<Integer> redRepeats = nextProblemDate.getRedRepeats();
        List<Integer> blueRepeats = nextProblemDate.getBlueRepeats();
        List<Integer> redNotRepeats = new ArrayList(new HashSet(redRepeats));
        List<Integer> redRemainNums = getRedRemainNums(redNotRepeats);
        List<Integer> blueNotRepeats = new ArrayList(new HashSet(blueRepeats));
        List<Integer> blueRemainNums = getBlueRemainNums(blueNotRepeats);
        int reds = (int) Math.floor(redProblemAvg * redRepeats.size());
        int blues = (int) Math.floor(blueProblemAvg * blueRepeats.size());

        //1、将红球的重复数字拿出，将篮球的重复数字拿出
        //2、将红球非重复的数字拿出，将篮球的非重复数字拿出
        int redsRemain = DaletouCount.REDS.getValue() - reds;
        int bluesRemain = DaletouCount.BLUES.getValue() - blues;
        List<Integer> redRandomRepeats = getProblems(redNotRepeats, reds);
        List<Integer> blueRandomRepeats = getProblems(blueNotRepeats, blues);
        List<Integer> redRandomRemains = getProblems(redRemainNums, redsRemain);
        List<Integer> blueRandomRemains = getProblems(blueRemainNums, bluesRemain);

        daletouBo.getRed().addAll(redRandomRepeats);
        daletouBo.getRed().addAll(redRandomRemains);
        daletouBo.getBlue().addAll(blueRandomRepeats);
        daletouBo.getBlue().addAll(blueRandomRemains);
        daletouBo.sort();

        return daletouBo;
    }

    /**
     * 获取蓝色的重复后余下的数字列表
     *
     * @param source 蓝色的重复列表
     * @return 余下的列表
     */
    private List<Integer> getBlueRemainNums(List<Integer> source) {
        if (CollectionUtils.isEmpty(source)) {
            source = new ArrayList<Integer>();
        }
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= DaletouNumCount.BLUES.getValue(); i++) {
            if (!source.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 获取红色的重复后余下的数字列表
     *
     * @param source 红色的重复列表
     * @return 余下的列表
     */
    private List<Integer> getRedRemainNums(List<Integer> source) {
        if (CollectionUtils.isEmpty(source)) {
            source = new ArrayList<Integer>();
        }
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= DaletouNumCount.REDS.getValue(); i++) {
            if (!source.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 根据列表获取N个随机数
     *
     * @param source    源列表
     * @param randomNum N个随机数
     * @return 随机数列表
     */
    private List<Integer> getProblems(List<Integer> source, int randomNum) {
        if (0 == randomNum) {
            return new ArrayList<Integer>();
        }
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < randomNum; i++) {
            int size = source.size();
            int index = (int) (Math.random() * size);
            index = index == size ? index - 1 : index;
            Integer value = source.get(index);
            result.add(value);
            source.remove(index);
        }
        return result;
    }

    /**
     * 获取下一个大乐透的重合度
     *
     * @param problemDateMap 重合度集合
     * @param daletouId      大乐透ID
     * @return 重合度
     */
    private ProblemDate getNextProblemDate(Map<Integer, ProblemDate> problemDateMap, int daletouId) {
        Assert.notNull(problemDateMap, "不能为空");
        ProblemDate problemDate = null;
        while (daletouId >= DaletouConstant.MIN_DALETOU_ID) {
            problemDate = problemDateMap.get(daletouId);
            if (null != problemDate) {
                break;
            } else {
                daletouId--;
            }
        }

        return problemDate;
    }


    /**
     * 预测指定期号的大乐透列表
     *
     * @param count          大乐透的数量
     * @param num            获取样本的数量
     * @param daletouId      指定的大乐透期号
     * @param problemDates   历史概率
     * @param historyRepeats 历史重复数
     * @return 预测的大乐透
     */
    public List<DaletouBo> forecastDaletou(int count, int num, int daletouId, List<ProblemDate> problemDates, List<HistoryDate> historyRepeats) {
        List<DaletouBo> result = new ArrayList<DaletouBo>();
        for (int i = 0; i < num; i++) {
            DaletouBo daletouBo = forecastDaletou(num, daletouId, problemDates, historyRepeats);
            result.add(daletouBo);
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
        return CollectionCommonUtil.asList(beforeSort, ProblemDate.class);
    }
}
