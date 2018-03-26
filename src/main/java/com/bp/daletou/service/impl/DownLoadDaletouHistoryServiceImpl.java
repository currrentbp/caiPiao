package com.bp.daletou.service.impl;

import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.DownLoadDaletouHistoryService;
import com.bp.util.ParseUrl;
import com.currentbp.util.all.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 下载大乐透历史数据
 *
 * @author current_bp
 * @createTime 20180128
 */
@Service("downLoadDaletouHistoryService")
public class DownLoadDaletouHistoryServiceImpl implements DownLoadDaletouHistoryService {

    private final static Logger logger = LoggerFactory.getLogger(DownLoadDaletouHistoryServiceImpl.class);

    /**
     * 下载最新的大乐透的历史数据
     *
     * @param startDaletouId 最开始下载的大乐透ID
     * @return 大乐透列表
     */
    @Override
    public List<DaletouEntity> downLoadNewDaletouHistory(Integer startDaletouId) {
        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        int max = ((startDaletouId / 100) * 100) + 155;
        for (int i = startDaletouId; i <= max; i++) {
            DaletouEntity daletouEntity = downLoadDaletouHistory(i);
            if (null != daletouEntity) {
                result.add(daletouEntity);
            }
        }
        return result;
    }

    /**
     * 下载当个大乐透
     *
     * @param daletouId 大乐透ID
     * @return 大乐透
     */
    @Override
    public DaletouEntity downLoadDaletouHistory(Integer daletouId) {
        String url = "http://kaijiang.500.com/shtml/dlt/" + String.format("%05d", daletouId) + ".shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> result = new ParseUrl().getParseContents(url, css);

        DaletouEntity daletouEntity = null;
        try {
            daletouEntity = new DaletouEntity(daletouId, result);
        } catch (Exception e) {
            logger.error("download daletou is error! id:" + daletouId);
            daletouEntity  = null;
        }
        return daletouEntity;
    }

    /**
     * 下载所有的大乐透
     *
     * @return 大乐透列表
     */
    @Override
    public List<DaletouEntity> downLoadAllDaletouHistory() {
        int start = 2007;//从07年开始有大乐透
        int endYear = TimeUtil.getCurrentYear();

        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        for (; start <= endYear; start++) {
            List<DaletouEntity> daletouEntities = downLoadNewDaletouHistory((start % 2000) * 1000 + 1);
            result.addAll(daletouEntities);
        }


        return result;
    }
}
