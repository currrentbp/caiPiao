package com.currentbp.daletou.facade.impl;

import com.currentbp.api.daletou.facade.DaletouServiceFacade;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.condition.DaletouPageCondition;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.entity.UserDaletou;
import com.currentbp.daletou.service.common.UserDaletouService;
import com.currentbp.daletou.service.v1.DaletouServiceVOne;
import com.currentbp.daletou.service.v2.DaletouServiceVTwo;
import com.currentbp.vo.Win;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180418
 */
public class DaletouServiceFacadeImpl implements DaletouServiceFacade {
    @Autowired
    private DaletouServiceVOne daletouServiceVOne;
    @Autowired
    private DaletouServiceVTwo daletouServiceVTwo;
    @Autowired
    private UserDaletouService userDaletouService;

    @Override
    public List<Daletou> queryDaletouAll() {
        return daletouServiceVOne.queryDaletouAll();
    }

    @Override
    public List<Daletou> queryDaletouByCondition(DaletouPageCondition daletouPageCondition) {
        return daletouServiceVOne.queryDaletouByCondition(daletouPageCondition);
    }

    @Override
    public List<Win> isWin(List<Daletou> daletous) {
        return daletouServiceVOne.isWin(daletous);
    }

    @Override
    public List<Daletou> forecastV1(int num, int daletouId) {
        List<Daletou> daletous = daletouServiceVOne.forecastV1(num, daletouId);
        daletous.forEach(daletou -> {
            UserDaletou userDaletou = new UserDaletou();
            userDaletou.setDaletouId(daletouId);
            DaletouBo daletouBo = new DaletouBo(daletou);
            userDaletou.setDaletou(daletouBo.toString());
            userDaletou.setUserId(0L);
            userDaletou.setForecastVersion(1);
        });
        return daletous;
    }

    @Override
    public List<Daletou> forecastV2(int num, List<Daletou> daletous,Integer daletouId) {
        List<Daletou> newDaletous = daletouServiceVTwo.forecastV2(num, daletous);
        newDaletous.forEach(daletou -> {
            UserDaletou userDaletou = new UserDaletou();
            userDaletou.setDaletouId(daletouId);
            DaletouBo daletouBo = new DaletouBo(daletou);
            userDaletou.setDaletou(daletouBo.toString());
            userDaletou.setUserId(0L);
            userDaletou.setForecastVersion(1);
        });
        return newDaletous;
    }
}
