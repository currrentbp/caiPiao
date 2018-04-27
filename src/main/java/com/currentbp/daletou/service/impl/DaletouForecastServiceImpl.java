package com.currentbp.daletou.service.impl;

import com.currentbp.daletou.dao.DaletouForecastDao;
import com.currentbp.daletou.entity.DaletouForecast;
import com.currentbp.daletou.service.DaletouForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 大乐透预测数据的服务
 *
 * @author current_bp
 * @createTime 20180426
 */
@Service
public class DaletouForecastServiceImpl implements DaletouForecastService {
    @Autowired
    private DaletouForecastDao daletouForecastDao;

    @Override
    public void createTable(Integer daletouId) {
        daletouForecastDao.createTable(daletouId);
    }

    @Override
    public void batchInsert(Integer daletouId, List<DaletouForecast> daletouForecasts) {
        daletouForecastDao.batchInsert(daletouId, daletouForecasts);
    }

    @Override
    public List<DaletouForecast> queryByIds(Integer daletouId, List<Integer> ids) {
        return daletouForecastDao.queryByIds(daletouId, ids);
    }

    @Override
    public List<DaletouForecast> queryAll(Integer daletouId) {
        return daletouForecastDao.queryAll(daletouId);
    }
}
