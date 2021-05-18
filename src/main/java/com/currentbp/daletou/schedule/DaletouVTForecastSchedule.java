package com.currentbp.daletou.schedule;

import com.currentbp.daletou.dao.DaletouDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 第二种预测方式的定时预测任务
 * @author baopan
 * @createTime 20210507
 */
@Component
public class DaletouVTForecastSchedule {
    private final static Logger logger = LoggerFactory.getLogger(DaletouVTForecastSchedule.class);
    @Autowired
    private DaletouDao daletouDao;

    @Scheduled(cron = "0 0 * * * ?") // 间隔1小时执行
    public void forecastDaletou() {
        logger.info("===>forecastDaletou is start.....");
        Integer lastedDaletouId = daletouDao.queryMaxId()+1;
        

        logger.info("===>forecastDaletou is end.....");
    }
}
