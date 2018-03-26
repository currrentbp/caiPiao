package com.bp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.bp.common.entity.DaletouEntity;
import com.bp.common.entity.HistoryDate;
import com.bp.common.entity.ProblemDate;
import com.bp.daletou.service.AnalysisHistoryService;
import com.bp.daletou.service.ForecastDaletouService;
import com.bp.daletou.service.InitDaletouService;
import com.currentbp.util.all.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import com.bp.BaseTest;
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