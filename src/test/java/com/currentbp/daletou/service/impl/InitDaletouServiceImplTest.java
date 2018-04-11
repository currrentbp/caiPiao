package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.daletou.service.InitDaletouService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author current_bp
 * @createTime 20180125
 */
public class InitDaletouServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(InitDaletouServiceImplTest.class);

    @Autowired
    public InitDaletouService initDaletouService;

    @Test
    public void getDaletouHistoryFromLocal(){
        logger.info(JSON.toJSONString(initDaletouService.getDaletouHistoryFromLocal()));
    }
}