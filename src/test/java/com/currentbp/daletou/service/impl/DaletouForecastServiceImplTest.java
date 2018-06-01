package com.currentbp.daletou.service.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.entity.DaletouForecast;
import com.currentbp.daletou.service.DaletouForecastService;
import com.currentbp.util.all.Assert;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180426
 */
public class DaletouForecastServiceImplTest extends BaseTest {
    @Autowired
    private DaletouForecastService daletouForecastService;
    private static Logger logger = LoggerFactory.getLogger(DaletouForecastServiceImplTest.class);


    @Test
    public void createTable() throws Exception {
        daletouForecastService.createTable(18058);
    }

    @Test
    public void batchInsert() throws Exception {
        List<DaletouForecast> daletouForecasts = new ArrayList<>();
        DaletouForecast daletouForecast = new DaletouForecast();
        daletouForecast.setForecast("1,2,3,4,5;6,7");
        daletouForecast.setUsedCount(0);
        daletouForecasts.add(daletouForecast);
        daletouForecastService.batchInsert(18047, daletouForecasts);
    }

    @Test
    public void queryByIds() throws Exception {
        List<Integer> ids = Lists.newArrayList(1);
        List<DaletouForecast> daletouForecasts = daletouForecastService.queryByIds(18047, ids);
        Assert.notEmpty(daletouForecasts, "为空");
    }

}