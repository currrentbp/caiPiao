package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.HistoryDate;
import com.currentbp.daletou.bo.entity.ProblemDate;
import com.currentbp.daletou.service.AnalysisHistoryService;
import com.currentbp.daletou.service.ForecastDaletouService;
import com.currentbp.daletou.service.InitDaletouService;
import com.currentbp.util.all.Assert;
import org.junit.Test;

import com.currentbp.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author baopan
 * @createTime 20180305
 */
public class ForecastDaletouBoServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(ForecastDaletouBoServiceImplTest.class);
    @Autowired
    private ForecastDaletouService forecastDaletouService;
    @Autowired
    private AnalysisHistoryService analysisHistoryService;
    @Autowired
    public InitDaletouService initDaletouService;

//    @Test
//    public void forecastDaletou() throws Exception {
//        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
//        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouBoHistoryFromLocal);
//        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouBoHistoryFromLocal, historyRepeatsFromHistory);
//        List<DaletouBo> daletouBoEntities = forecastDaletouService.forecastDaletou(5, 5, 18063, historyProblemDatesFromHistory, historyRepeatsFromHistory);
//        logger.info("===>result:" + daletouBoEntities);
//        Assert.notEmpty(daletouBoEntities, "is empty");
//    }

    @Test
    public void forecastDaletou4AllAndSave() throws Exception {
        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromRepository();
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouBoHistoryFromLocal);
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouBoHistoryFromLocal, historyRepeatsFromHistory);
        logger.info("===>historyProblemDatesFromHistory:" + JSON.toJSONString(historyProblemDatesFromHistory));
        forecastDaletouService.forecastDaletou4AllAndSave(5, 18064, historyProblemDatesFromHistory, historyRepeatsFromHistory);
        Thread.sleep(100000);
    }

    /**
     * 批量的将预测数据插入数据库，便于分析数据
     * @throws Exception
     */
    @Test
    public void forecastAllDaletou4AllAndSave() throws Exception {
        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromRepository();
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouBoHistoryFromLocal);
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouBoHistoryFromLocal, historyRepeatsFromHistory);
        logger.info("===>historyProblemDatesFromHistory:" + JSON.toJSONString(historyProblemDatesFromHistory));
        for (DaletouBo daletouBo : daletouBoHistoryFromLocal) {
            forecastDaletouService.forecastDaletou4AllAndSave(5, daletouBo.getId(), historyProblemDatesFromHistory, historyRepeatsFromHistory);
        }
        Thread.sleep(100000);
    }


}