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
//        daletous.add(new Daletou(13,15,17,23,34,1,11));
        daletous.add(new Daletou(4,16,27,28,35,2,9));
        List<Daletou> daletous1 = daletouServiceFacade.forecastV2(5, daletous, 21060);
        logger.info("===>result :"+ daletous1.toString());
    }
}