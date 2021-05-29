package com.currentbp.daletou.schedule;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.service.common.DownLoadDaletouHistoryService;
import com.currentbp.daletou.service.v1.DaletouServiceVOne;
import com.currentbp.util.all.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 下载最新的大乐透数据
 *
 * @author baopan
 * @createTime 20210411
 */
@Component
public class DownLoadLastedDaletouSchedule {
    private final static Logger logger = LoggerFactory.getLogger(DownLoadLastedDaletouSchedule.class);
    @Autowired
    private DaletouDao daletouDao;
    @Autowired
    private DownLoadDaletouHistoryService downLoadDaletouHistoryService;
    @Autowired
    private DaletouServiceVOne daletouServiceVOne;


    //    @Scheduled(cron = "0 0 * * * ?") // 间隔1小时执行
    public void downloadDaletouTask() {
        logger.info("===>task is start.....");
        int lastedId = getLastedId();
        logger.info("downloadDaletou, lastedId:{}", lastedId);
        DaletouBo daletouBo = downLoadDaletouHistoryService.downLoadDaletouHistory(lastedId);
        if (null != daletouBo) {
            daletouServiceVOne.insert(daletouBo.toDaletou());
        }

        logger.info("===>task is end.....");
    }

    private int getLastedId() {
        int remainYear = TimeUtil.getCurrentYear() % 100;
        int minNum = remainYear * 1000 + 1;
        int maxNum = (1 + remainYear) * 1000;

        Integer maxId = daletouDao.queryMaxIdFromMinIdAndMaxId(minNum, maxNum);
        if (null == maxId) {
            return minNum;
        } else {
            return maxId + 1;
        }
    }

}
