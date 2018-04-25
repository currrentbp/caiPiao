package com.currentbp.daletou.service.impl;

import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.DaletouService;
import com.currentbp.daletou.service.DownLoadDaletouHistoryService;
import com.currentbp.util.all.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180418
 */
public class DaletouServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(DaletouServiceImplTest.class);

    @Autowired
    private DownLoadDaletouHistoryService daletouHistoryService;
    @Autowired
    private DaletouService daletouService;

    @Test
    public void insertAll() throws Exception {
        List<DaletouBo> daletouBos = daletouHistoryService.downLoadAllDaletouHistory();
        Assert.notNull(daletouBos, "===>is null");
        for (DaletouBo daletouBo : daletouBos) {
            Daletou daletou = null;
            try {
                daletou = daletouBo.toDaletou();
            } catch (Exception e) {
                continue;
            }
            daletouService.insert(daletou);
        }
    }

    @Test
    public void insertSome() throws Exception {
        List<DaletouBo> daletouBos = daletouHistoryService.downLoadNewDaletouHistory(18044);
        for (DaletouBo daletouBo : daletouBos) {
            Daletou daletou = null;
            try {
                daletou = daletouBo.toDaletou();
            } catch (Exception e) {
                continue;
            }
            daletouService.insert(daletou);
        }
    }


    @Test
    public void queryDaletouAll() throws Exception {
    }

}