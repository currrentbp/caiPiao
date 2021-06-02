package com.currentbp.daletou.service.v3.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v3.DaletouServiceVThree;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DaletouServiceVThreeImplTest extends BaseTest {

    @Autowired
    private DaletouServiceVThree daletouServiceVThree;

    @Test
    public void t1(){
        List<Daletou> daletous = daletouServiceVThree.forecastV3(21060, 5);
        System.out.println(daletous.toString());
    }
}