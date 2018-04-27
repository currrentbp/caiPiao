package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.AnalysisDaletouForecast;
import com.currentbp.daletou.service.AnalysisDaletouForecastService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180427
 */
public class AnalysisDaletouForecastServiceImplTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(AnalysisDaletouForecastServiceImplTest.class);
    @Autowired
    private AnalysisDaletouForecastService analysisDaletouForecastService;

    @Test
    public void analysisDaletouForecast() throws Exception {
        AnalysisDaletouForecast analysisDaletouForecast = analysisDaletouForecastService.analysisDaletouForecast(18046);
        logger.info("===>" + JSON.toJSONString(analysisDaletouForecast));
    }

}