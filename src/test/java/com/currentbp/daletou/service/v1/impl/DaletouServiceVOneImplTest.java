package com.currentbp.daletou.service.v1.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.BaseTest;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v1.DaletouForecastService;
import com.currentbp.daletou.service.v1.DaletouServiceVOne;
import com.currentbp.daletou.service.common.DownLoadDaletouHistoryService;
import com.currentbp.util.all.Assert;
import com.currentbp.vo.Win;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author current_bp
 * @createTime 20180418
 */
public class DaletouServiceVOneImplTest extends BaseTest {


    private final static Logger logger = LoggerFactory.getLogger(DaletouServiceVOneImplTest.class);

    @Autowired
    private DownLoadDaletouHistoryService daletouHistoryService;
    @Autowired
    private DaletouServiceVOne daletouServiceVOne;
    @Autowired
    private DaletouForecastService daletouForecastService;

    /***
     * 适用于第一次插入数据
     * @throws Exception
     */
    @Test
    public void insertAll() throws Exception {
        List<DaletouBo> daletouBos = daletouHistoryService.downLoadAllDaletouHistory();
        Assert.notNull(daletouBos, "===>is null");
        for (DaletouBo daletouBo : daletouBos) {
            Daletou daletou = null;
            try {
                daletou = daletouBo.toDaletou();
                daletouServiceVOne.insert(daletou);
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 适用于后续追加大乐透
     *
     * @throws Exception
     */
    @Test
    public void insertSome() throws Exception {
        List<DaletouBo> daletouBos = daletouHistoryService.downLoadNewDaletouHistory(20098);
        for (DaletouBo daletouBo : daletouBos) {
            Daletou daletou = null;
            try {
                daletou = daletouBo.toDaletou();
                daletouServiceVOne.insert(daletou);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Test
    public void insertCurrent() {
        DaletouBo daletouBo = daletouHistoryService.downLoadDaletouHistory(20098);
        Daletou daletou = daletouBo.toDaletou();
        daletouServiceVOne.insert(daletou);

    }


    @Test
    public void queryDaletouAll() throws Exception {
    }

    /**
     * 判断所有的预测数据中奖的概率
     *
     * @throws Exception
     */
    @Test
    public void isWin() throws Exception {
        List<Daletou> daletous = new ArrayList<>();
        Daletou daletou1 = new DaletouBo(18073, "1,12,21,34,35;2,7").toDaletou();
        Daletou daletou2 = new DaletouBo(18073, "12,32,33,34,35;2,12").toDaletou();
        Daletou daletou3 = new DaletouBo(18073, "1,2,3,5,34;2,12").toDaletou();
        Daletou daletou4 = new DaletouBo(18073, "1,2,6,26,35;1,2").toDaletou();
        Daletou daletou5 = new DaletouBo(18073, "1,2,8,24,35;2,10").toDaletou();
        Daletou daletou6 = new DaletouBo(18073, "1,2,5,23,35;2,12").toDaletou();
        daletous.add(daletou1);
        daletous.add(daletou2);
        daletous.add(daletou3);
        daletous.add(daletou4);
        daletous.add(daletou5);
        daletous.add(daletou6);
        List<Win> win = daletouServiceVOne.isWin(daletous);
        logger.info(JSON.toJSONString(win));
    }

}