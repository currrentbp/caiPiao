package com.currentbp.daletou.service.v1.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.service.common.DownLoadDaletouHistoryService;
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
public class DownLoadDaletouBoHistoryServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(DownLoadDaletouBoHistoryServiceImplTest.class);

    @Autowired
    private DownLoadDaletouHistoryService daletouHistoryService;


//    @Test
//    public void downLoadNewDaletouHistory() throws Exception {
//        List<DaletouBo> daletouBoEntities = daletouHistoryService.downLoadNewDaletouHistory(20098);
//        logger.info(JSON.toJSONString(daletouBoEntities));
//        Assert.notEmpty(daletouBoEntities, "is empty");
//    }
//
//    @Test
//    public void downLoadDaletouHistory() throws Exception {
//        DaletouBo daletouBo = daletouHistoryService.downLoadDaletouHistory(18031);
//        logger.info(JSON.toJSONString(daletouBo));
//        Assert.notNull(daletouBo, "===>is null");
//    }
//
//    @Test
//    public void downLoadAllDaletouHistory(){
//        List<DaletouBo> daletouBoEntities = daletouHistoryService.downLoadAllDaletouHistory();
//        Assert.notNull(daletouBoEntities, "===>is null");
//    }

}