package com.bp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.bp.BaseTest;
import com.bp.common.entity.DaletouEntity;
import com.bp.common.entity.HistoryDate;
import com.bp.common.entity.ProblemDate;
import com.bp.daletou.service.AnalysisHistoryService;
import com.bp.daletou.service.InitDaletouService;
import com.currentbp.util.all.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180128
 */
public class AnalysisHistoryServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(AnalysisHistoryServiceImpl.class);
    @Autowired
    private AnalysisHistoryService analysisHistoryService;
    @Autowired
    public InitDaletouService initDaletouService;


    @Test
    public void getHistoryRepeatsFromHistory() throws Exception {
        List<DaletouEntity> daletouHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouHistoryFromLocal);
        Assert.notEmpty(historyRepeatsFromHistory, "is empty");
    }

    @Test
    public void getHistoryProblemDatesFromHistory() {
        List<DaletouEntity> daletouHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouHistoryFromLocal);
        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouHistoryFromLocal, historyRepeatsFromHistory);
        logger.info("===>" + JSON.toJSONString(historyProblemDatesFromHistory));
        Assert.notEmpty(historyProblemDatesFromHistory, "is empty");
    }

}