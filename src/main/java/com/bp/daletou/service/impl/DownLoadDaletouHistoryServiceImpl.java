package com.bp.daletou.service.impl;

import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.DownLoadDaletouHistoryService;
import com.bp.util.ParseUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<DaletouEntity> downLoadNewDaletouHistory(Integer startDaletouId) {
        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        logger.info(""+(startDaletouId%100));
        int max = ((startDaletouId/100) *100) + 155;
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
    public DaletouEntity downLoadDaletouHistory(Integer daletouId) {
        String url = "http://kaijiang.500.com/shtml/dlt/" + String.format("%05d", daletouId) + ".shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> result = new ParseUrl().getParseContents(url, css);

        DaletouEntity daletouEntity = null;
        try {
            daletouEntity = new DaletouEntity(daletouId, result);
        } catch (Exception e) {
            logger.error("download daletou is error! id:" + daletouId);
        }
        logger.debug("===>daletouEntity :" + daletouEntity);

        return daletouEntity;
    }
}
