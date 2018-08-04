package com.currentbp.daletou.service.impl;

import com.alibaba.fastjson.JSON;
import com.currentbp.daletou.bo.entity.AnalysisDaletouForecast;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.entity.DaletouForecast;
import com.currentbp.daletou.service.AnalysisDaletouForecastService;
import com.currentbp.daletou.service.DaletouForecastService;
import com.currentbp.daletou.service.DaletouService;
import com.currentbp.vo.Win;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分析大乐透预测结果
 *
 * @author current_bp
 * @createTime 20180427
 */
@Service
public class AnalysisDaletouForecastServiceImpl implements AnalysisDaletouForecastService {
    private final static Logger logger = LoggerFactory.getLogger(AnalysisDaletouForecastServiceImpl.class);
    @Autowired
    private DaletouDao daletouDao;
    @Autowired
    private DaletouForecastService daletouForecastService;
    @Autowired
    private DaletouService daletouService;


    @Override
    public AnalysisDaletouForecast analysisDaletouForecast(Integer daletouId) {
        AnalysisDaletouForecast analysisDaletouForecast = new AnalysisDaletouForecast();
        analysisDaletouForecast.setDaletouId(daletouId);

        Daletou daletou = daletouDao.queryById(daletouId);
        List<DaletouForecast> daletouForecasts = daletouForecastService.queryAll(daletouId);

        Integer count = 0;//总中奖次数
        Long sum = 0L;//总中奖金额
        Integer personCount = 0;//总中奖人次
        Integer forecastCount = daletouForecasts.size();//总预测数量

        for (DaletouForecast daletouForecast : daletouForecasts) {
            try {
                Daletou forecastDaletou = new DaletouBo(daletouId, daletouForecast.getForecast()).toDaletou();
                Win win = daletouService.isWin(forecastDaletou, daletou);

                if (win.isWin()) {
                    count++;
                    personCount += daletouForecast.getUsedCount();
                    int index = win.getWinType()-1;
                    Integer nowCount = analysisDaletouForecast.getWinCount().get(index);
                    analysisDaletouForecast.getWinCount().set(index, ++nowCount);
                    sum = sum + win.getBaseMoney();
                }
            } catch (Exception e) {
                //logger.error("daletouForecast:" + JSON.toJSONString(daletouForecast) + " msg:" + e.getMessage());
            }
        }
        analysisDaletouForecast.setForecastCount(forecastCount);
        analysisDaletouForecast.setCount(count);
        analysisDaletouForecast.setPersonCount(personCount);
        analysisDaletouForecast.setSum(sum);

        return analysisDaletouForecast;
    }
}
