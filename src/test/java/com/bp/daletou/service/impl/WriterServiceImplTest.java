package com.bp.daletou.service.impl;

import com.bp.BaseTest;
import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.DownLoadDaletouHistoryService;
import com.bp.daletou.service.WriterService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        List<DaletouEntity> daletouEntities = downLoadDaletouHistoryService.downLoadAllDaletouHistory();
        writerService.writeDaletouHistory2Local(daletouEntities);
    }

    @Test
    public void writeDaletouHistory2Local2() throws Exception {
        List<DaletouEntity> daletouEntities = daletouHistoryService.downLoadNewDaletouHistory(18031);
        writerService.writeDaletouHistory2Local(daletouEntities);
    }

}