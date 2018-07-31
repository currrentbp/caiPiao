package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.AnalysisDaletouForecast;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.service.AnalysisDaletouForecastService;
import com.currentbp.daletou.service.InitDaletouService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180427
 */
public class AnalysisDaletouForecastServiceImplTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(AnalysisDaletouForecastServiceImplTest.class);
    @Autowired
    private AnalysisDaletouForecastService analysisDaletouForecastService;
    @Autowired
    public InitDaletouService initDaletouService;

    @Test
    public void analysisDaletouForecast() throws Exception {
        List<DaletouBo> daletouBoHistoryFromLocal = initDaletouService.getDaletouHistoryFromRepository();
        for (DaletouBo daletouBo : daletouBoHistoryFromLocal) {
            AnalysisDaletouForecast analysisDaletouForecast = analysisDaletouForecastService.analysisDaletouForecast(daletouBo.getId());
            logger.info("===>" + JSON.toJSONString(analysisDaletouForecast));
        }
    }

}