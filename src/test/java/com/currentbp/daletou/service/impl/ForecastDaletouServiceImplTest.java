package com.currentbp.daletou.service.impl;

import com.currentbp.entity.DaletouEntity;
import com.currentbp.entity.HistoryDate;
import com.currentbp.entity.ProblemDate;
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
public class ForecastDaletouServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(ForecastDaletouServiceImplTest.class);
    @Autowired
    private ForecastDaletouService forecastDaletouService;
    @Autowired
    private AnalysisHistoryService analysisHistoryService;
    @Autowired
    public InitDaletouService initDaletouService;

    @Test
    public void forecastDaletou() throws Exception {
        List<DaletouEntity> daletouHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouHistoryFromLocal);
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouHistoryFromLocal, historyRepeatsFromHistory);
        List<DaletouEntity> daletouEntities = forecastDaletouService.forecastDaletou(5, 5, 18034, historyProblemDatesFromHistory, historyRepeatsFromHistory);
        logger.info("===>result:" + daletouEntities);
        Assert.notEmpty(daletouEntities, "is empty");
    }


}