package com.currentbp.daletou.service.impl;

import com.currentbp.constant.WinType;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.HistoryDate;
import com.currentbp.daletou.bo.entity.ProblemDate;
import com.currentbp.daletou.condition.DaletouCondition;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.AnalysisHistoryService;
import com.currentbp.daletou.service.DaletouService;
import com.currentbp.daletou.service.ForecastDaletouService;
import com.currentbp.util.all.CollectionCommonUtil;
import com.currentbp.vo.Win;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author current_bp
 * @createTime 20180418
 */
@Service
public class DaletouServiceImpl implements DaletouService {
    @Autowired
    private DaletouDao daletouDao;
    @Autowired
    private ForecastDaletouService forecastDaletouService;
    @Autowired
    private AnalysisHistoryService analysisHistoryService;

    private static Integer SAMPLE_SIZE = 5;

    @Override
    public void insert(Daletou daletou) {
        daletouDao.insert(daletou);
    }

    @Override
    public List<Daletou> queryDaletouAll() {
        return daletouDao.queryAll();
    }

    @Override
    public List<Daletou> queryDaletouByCondition(DaletouCondition daletouCondition) {
        return daletouDao.queryByCondition(daletouCondition);
    }

    @Override
    public List<Win> isWin(List<Daletou> daletous) {
        List<Integer> daletouIds = CollectionCommonUtil.getFieldListByMethodName(daletous, "getId", Integer.class);
        List<Daletou> source = daletouDao.queryByIds(daletouIds);
        Map<Integer, Daletou> sourceMap = CollectionCommonUtil.getMapFromListByMethodName(source, "getId", Integer.class);

        List<Win> result = daletous.stream().map(daletou -> {
            return isWin(daletou, sourceMap.get(daletou.getId()));
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Daletou> forecast(int num, int daletouId) {
        //获取大乐透列表
        List<Daletou> daletous = daletouDao.queryAll();
        List<DaletouBo> daletoBos = daletous.stream().map((daletou) -> {
            return new DaletouBo(daletou);
        }).collect(Collectors.toList());

        //获取前N期的重复数据
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(SAMPLE_SIZE, daletoBos);
        //获取重复数据的概率
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletoBos, historyRepeatsFromHistory);
        //预测数据
        List<DaletouBo> daletouBos = forecastDaletouService.forecastDaletou(num, SAMPLE_SIZE, daletouId, historyProblemDatesFromHistory, historyRepeatsFromHistory);
        List<Daletou> result = daletouBos.stream().map(daletouBo -> {
            return daletouBo.toDaletou();
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void forecastAndSave(int daletouId) {
        //获取大乐透列表
        List<Daletou> daletous = daletouDao.queryAll();
        List<DaletouBo> daletoBos = daletous.stream().map((daletou) -> {
            return new DaletouBo(daletou);
        }).collect(Collectors.toList());

        //获取前N期的重复数据
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(SAMPLE_SIZE, daletoBos);
        //获取重复数据的概率
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletoBos, historyRepeatsFromHistory);
        //预测数据
        forecastDaletouService.forecastDaletou4AllAndSave(SAMPLE_SIZE, daletouId, historyProblemDatesFromHistory, historyRepeatsFromHistory);

    }

    /**
     * 判断是否中奖，以及中奖情况
     *
     * @param daletou 需要预测的大乐透
     * @param source  大乐透
     * @return 是否中奖
     */
    @Override
    public Win isWin(Daletou daletou, Daletou source) {
        DaletouBo daletouBo = new DaletouBo(daletou);
        DaletouBo sourceDaletouBo = new DaletouBo(source);

        List<Integer> reds = theSame(daletouBo.getRed(), sourceDaletouBo.getRed());
        List<Integer> blues = theSame(daletouBo.getBlue(), sourceDaletouBo.getBlue());
        Win win = new Win();
        win.setBlues(blues);
        win.setReds(reds);
        win.setRedWin(reds.size());
        win.setBlueWin(blues.size());
        WinType.DaletouWinType daletouWin = isDaletouWin(reds.size(), blues.size());
        win.setMsg(daletouWin.getValue());
        win.setWin(0 != daletouWin.getType());
        win.setSource(source);
        win.setWinType(daletouWin.getType());
        return win;
    }

    //=====================            private function                  =====================================================



    /**
     * 查询出相同的部分
     *
     * @param forecast 预测数据
     * @param source   源数据
     * @return 结果
     */
    private List<Integer> theSame(List<Integer> forecast, List<Integer> source) {
        return forecast.stream().filter(f -> source.indexOf(f) >= 0).collect(Collectors.toList());
    }

    /**
     * 计算大乐透的中间类型
     *
     * @param reds  红球数
     * @param blues 蓝球数
     * @return 中奖结果
     */
    private WinType.DaletouWinType isDaletouWin(int reds, int blues) {
        boolean redsIsFive = 5 == reds;
        boolean redsIsFour = 4 == reds;
        boolean redsIsThree = 3 == reds;
        boolean bluesIsZero = 0 == blues;
        boolean bluesIsOne = 1 == blues;
        boolean bluesIsTwo = 2 == blues;
        boolean redsIsOne = 1 == reds;
        boolean redsIsTwo = 2 == reds;
        boolean redsIsZero = 0 == reds;
        if (redsIsFive && bluesIsTwo) {
            return WinType.DaletouWinType.ONE;
        } else if (redsIsFive && bluesIsOne) {
            return WinType.DaletouWinType.TWO;
        } else if ((redsIsFive && bluesIsZero) || (redsIsFour && bluesIsTwo)) {
            return WinType.DaletouWinType.THREE;
        } else if ((redsIsFour && bluesIsOne) || (redsIsThree || bluesIsTwo)) {
            return WinType.DaletouWinType.FOUR;
        } else if ((redsIsFour && bluesIsZero) || (redsIsThree && bluesIsOne) || (redsIsTwo && bluesIsTwo)) {
            return WinType.DaletouWinType.FIVE;
        } else if ((redsIsThree && bluesIsZero) || (redsIsOne && bluesIsTwo) || (redsIsTwo && bluesIsOne) || (redsIsZero && bluesIsTwo)) {
            return WinType.DaletouWinType.SIX;
        }
        return WinType.DaletouWinType.ZERO;
    }
    /*
    可以优化的地方
    enum DLT {
 ZERO("", "未中奖"),
 ONE("52", "一等奖"),
 TWO("51", "二等奖"),
 THREE("50|42", "三等奖"),
 FOUR("41|32", "四等奖"),
 FIVE("40|31|22", "五等奖"),
 SIX("30|21|12|02", "六等奖"),
 ;
   private String pattern;
 private String name;
   DLT(String pattern, String name) {
 this.pattern = pattern;
 this.name = name;
 }
   public static DLT get(int reds, int blues){
 if (reds<0 || reds>5 || blues<0 || blues>2){
 throw new IllegalArgumentException("不符合大乐透中奖规则");
 }
 String s = ""+reds+blues;
 for (DLT dlt : DLT.values()) {
 if (dlt.getPattern().contains(s)){
 return dlt;
 }
 }
 return DLT.ZERO;
 }
   public String getPattern() {
 return pattern;
 }
   public String getName() {
 return name;
 }
 }
     */


}
