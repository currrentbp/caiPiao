package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.entity.DaletouEntity;
import com.currentbp.daletou.service.DownLoadDaletouHistoryService;
import com.currentbp.util.all.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180128
 */
public class DownLoadDaletouHistoryServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(DownLoadDaletouHistoryServiceImplTest.class);

    @Autowired
    private DownLoadDaletouHistoryService daletouHistoryService;


    @Test
    public void downLoadNewDaletouHistory() throws Exception {
        List<DaletouEntity> daletouEntities = daletouHistoryService.downLoadNewDaletouHistory(18031);
        logger.info(JSON.toJSONString(daletouEntities));
        Assert.notEmpty(daletouEntities, "is empty");
    }

    @Test
    public void downLoadDaletouHistory() throws Exception {
        DaletouEntity daletouEntity = daletouHistoryService.downLoadDaletouHistory(18031);
        logger.info(JSON.toJSONString(daletouEntity));
        Assert.notNull(daletouEntity, "===>is null");
    }

    @Test
    public void downLoadAllDaletouHistory(){
        List<DaletouEntity> daletouEntities = daletouHistoryService.downLoadAllDaletouHistory();
        Assert.notNull(daletouEntities, "===>is null");
    }

}