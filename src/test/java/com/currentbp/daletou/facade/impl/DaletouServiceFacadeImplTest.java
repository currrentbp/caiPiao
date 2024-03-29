package com.currentbp.daletou.facade.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.api.daletou.facade.DaletouServiceFacade;
import com.currentbp.daletou.entity.Daletou;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DaletouServiceFacadeImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(DaletouServiceFacadeImplTest.class);
    @Autowired
    private DaletouServiceFacade daletouServiceFacade;


    @Test
    public void testForecastV2() {
        List<Daletou> daletous = new ArrayList<>();
        daletous.add(new Daletou(7,23,33,34,35,7,9));
        daletous.add(new Daletou(4,11,29,32,33,3,6));
        List<Daletou> daletous1 = daletouServiceFacade.forecastV2(5, daletous, 21060);
        logger.info("===>result :"+ daletous1.toString());
    }
}