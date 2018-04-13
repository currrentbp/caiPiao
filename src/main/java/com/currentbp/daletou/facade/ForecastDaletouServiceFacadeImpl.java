package com.currentbp.daletou.facade;

import com.currentbp.api.daletou.facade.ForecastDaletouServiceFacade;
import com.currentbp.entity.DaletouEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 大乐透预测
 *
 * @author current_bp
 * @createTime 20180411
 */
public class ForecastDaletouServiceFacadeImpl implements ForecastDaletouServiceFacade {
    private final static Logger logger = LoggerFactory.getLogger(ForecastDaletouServiceFacadeImpl.class);

    @Override
    public List<DaletouEntity> forecastDaletou(int count, int daletouId) {
        logger.info("===>forecastDaletou :" + daletouId);
        return null;
    }
}
