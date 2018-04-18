package com.currentbp.daletou.service.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.service.DownLoadDaletouHistoryService;
import com.currentbp.daletou.service.WriterService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WriterServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(WriterServiceImplTest.class);
    @Autowired
    private WriterService writerService;
    @Autowired
    private DownLoadDaletouHistoryService downLoadDaletouHistoryService;
    @Autowired
    private DownLoadDaletouHistoryService daletouHistoryService;

    @Test
    public void writeDaletouHistory2Local() throws Exception {
        List<DaletouBo> daletouBoEntities = downLoadDaletouHistoryService.downLoadAllDaletouHistory();
        writerService.writeDaletouHistory2Local(daletouBoEntities);
    }

    @Test
    public void writeDaletouHistory2Local2() throws Exception {
        List<DaletouBo> daletouBoEntities = daletouHistoryService.downLoadNewDaletouHistory(18031);
        writerService.writeDaletouHistory2Local(daletouBoEntities);
    }

}