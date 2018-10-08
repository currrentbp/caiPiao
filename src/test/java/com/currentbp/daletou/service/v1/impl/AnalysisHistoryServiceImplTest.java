package com.currentbp.daletou.service.v1.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.service.v1.AnalysisHistoryService;
import com.currentbp.daletou.service.v1.InitDaletouService;
import com.currentbp.daletou.service.v1.impl.AnalysisHistoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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


//    @Test
//    public void getHistoryRepeatsFromHistory() throws Exception {
//        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
//        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouBoHistoryFromLocal);
//        Assert.notEmpty(historyRepeatsFromHistory, "is empty");
//    }
//
//    @Test
//    public void getHistoryProblemDatesFromHistory() {
//        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromLocal();
//        List<HistoryDate> historyRepeatsFromHistory = analysisHistoryService.getHistoryRepeatsFromHistory(5, daletouBoHistoryFromLocal);
//        List<ProblemDate> historyProblemDatesFromHistory = analysisHistoryService.getHistoryProblemDatesFromHistory(daletouBoHistoryFromLocal, historyRepeatsFromHistory);
//        logger.info("===>" + JSON.toJSONString(historyProblemDatesFromHistory));
//        Assert.notEmpty(historyProblemDatesFromHistory, "is empty");
//    }

}