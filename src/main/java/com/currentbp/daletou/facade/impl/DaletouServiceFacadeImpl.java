package com.currentbp.daletou.facade.impl;

import com.currentbp.api.daletou.facade.DaletouServiceFacade;
import com.currentbp.daletou.condition.DaletouCondition;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v1.DaletouService;
import com.currentbp.vo.Win;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180418
 */
public class DaletouServiceFacadeImpl implements DaletouServiceFacade {
    @Autowired
    private DaletouService daletouService;
    @Override
    public List<Daletou> queryDaletouAll() {
        return daletouService.queryDaletouAll();
    }

    @Override
    public List<Daletou> queryDaletouByCondition(DaletouCondition daletouCondition) {
        return daletouService.queryDaletouByCondition(daletouCondition);
    }

    @Override
    public List<Win> isWin(List<Daletou> daletous) {
        return daletouService.isWin(daletous);
    }

    @Override
    public List<Daletou> forecast(int num, int daletouId) {
        return daletouService.forecast(num,daletouId);
    }

    @Override
    public void forecastAndSave(int daletouId) {
        daletouService.forecastAndSave(daletouId);
    }
}
