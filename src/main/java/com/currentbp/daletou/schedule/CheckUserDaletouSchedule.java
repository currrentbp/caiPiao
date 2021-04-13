package com.currentbp.daletou.schedule;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.dao.UserDaletouDao;
import com.currentbp.daletou.dao.UserForecastResultDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.entity.UserDaletou;
import com.currentbp.daletou.entity.UserForecastResult;
import com.currentbp.daletou.service.common.DownLoadDaletouHistoryService;
import com.currentbp.daletou.service.v1.DaletouServiceVOne;
import com.currentbp.vo.Win;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查用户大乐透的中奖情况
 *
 * @author baopan
 * @createTime 20210413
 */
@Component
public class CheckUserDaletouSchedule {
    private final static Logger logger = LoggerFactory.getLogger(CheckUserDaletouSchedule.class);
    @Autowired
    private DaletouDao daletouDao;
    @Autowired
    private UserDaletouDao userDaletouDao;
    @Autowired
    private UserForecastResultDao userForecastResultDao;
    @Autowired
    private DownLoadDaletouHistoryService downLoadDaletouHistoryService;
    @Autowired
    private DaletouServiceVOne daletouServiceVOne;


    @Scheduled(cron = "0 0 * * * ?") // 间隔1小时执行
    public void checkUserDaletouTask() {
        logger.info("===>checkUserDaletouTask is start.....");

        Integer lastedDaletouId = daletouDao.queryMaxId();
        List<UserDaletou> userDaletouList = userDaletouDao.queryByDaletouId(lastedDaletouId);
        userDaletouList.forEach(this::doEach);

        logger.info("===>checkUserDaletouTask is end.....");
    }

    private void doEach(UserDaletou userDaletou) {
        if (userDaletou.getWin() != 0) {//已经分析过了，不需要分析了
            return;
        }
        DaletouBo daletouBo = new DaletouBo(userDaletou.getDaletou());
        List<Daletou> lists = new ArrayList<>();
        lists.add(daletouBo.toDaletou());
        List<Win> wins = daletouServiceVOne.isWin(lists);
        logger.info("checkUserDaletouTask, wins:{}", wins);
        Win win = wins.get(0);

        userDaletouDao.update(userDaletou.getId(), win.isWin());

        UserForecastResult userForecastResult = new UserForecastResult();
        userForecastResult.setUserForecastId(userDaletou.getId());
        userForecastResult.setCaipiaoType(1);//大乐透
        userForecastResult.setWin(win.isWin() ? 1 : 2);
        userForecastResult.setWinLevel(win.getWinType());
        userForecastResult.setBonus(win.getBaseMoney().longValue());
        userForecastResultDao.insert(userForecastResult);
    }


}
