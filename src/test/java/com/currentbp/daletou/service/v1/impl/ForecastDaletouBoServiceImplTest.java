package com.currentbp.daletou.service.v1.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.HistoryRepeatDate;
import com.currentbp.daletou.bo.entity.ProblemDate;
import com.currentbp.daletou.service.v1.AnalysisHistoryService;
import com.currentbp.daletou.service.v1.ForecastDaletouService;
import com.currentbp.daletou.service.v1.InitDaletouService;
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



}