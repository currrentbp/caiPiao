package com.bp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.bp.BaseTest;
import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.DownLoadDaletouHistoryService;
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
        List<DaletouEntity> daletouEntities = daletouHistoryService.downLoadNewDaletouHistory(18001);
        logger.info(JSON.toJSONString(daletouEntities));
        Assert.notEmpty(daletouEntities, "is empty");
    }

    @Test
    public void downLoadDaletouHistory() throws Exception {
        DaletouEntity daletouEntity = daletouHistoryService.downLoadDaletouHistory(18013);
        logger.info(JSON.toJSONString(daletouEntity));
        Assert.notNull(daletouEntity, "===>is null");
    }

}