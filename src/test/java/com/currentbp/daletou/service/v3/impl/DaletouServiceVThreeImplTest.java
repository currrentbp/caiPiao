package com.currentbp.daletou.service.v3.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.facade.impl.DaletouServiceFacadeImplTest;
import com.currentbp.daletou.service.v3.DaletouServiceVThree;
import com.currentbp.daletou.service.v3.DaletouServiceVThreeAndTwo;
import com.currentbp.util.all.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DaletouServiceVThreeImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(DaletouServiceVThreeImplTest.class);
    @Autowired
    private DaletouServiceVThree daletouServiceVThree;
    @Autowired
    private DaletouServiceVThreeAndTwo daletouServiceVThreeAndTwo;

    @Test
    public void t1() {
        List<Daletou> daletous = daletouServiceVThree.forecastV3(21063, 5);
        System.out.println(daletous.toString());
    }

    @Test
    public void t2() {
        List<Daletou> daletous = new ArrayList<>();
        daletous.add(new Daletou(2,4,9,26,27,7,9));
        daletous.add(new Daletou(10,14,17,20,32,1,11));
        List<Daletou> daletous1 = daletouServiceVThreeAndTwo.forecastV3_2(21063, 5, daletous);
        logger.info("===>result :"+ daletous1.toString());
    }
}