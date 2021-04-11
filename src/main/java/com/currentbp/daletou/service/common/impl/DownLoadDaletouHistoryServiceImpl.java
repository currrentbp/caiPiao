package com.currentbp.daletou.service.common.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.service.common.DownLoadDaletouHistoryService;
import com.currentbp.util.ParseUrl;
import com.currentbp.util.all.TimeUtil;
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
    @Override
    public List<DaletouBo> downLoadNewDaletouHistory(Integer startDaletouId) {
        List<DaletouBo> result = new ArrayList<>();
        int max = ((startDaletouId / 100) * 100) + 155;
        for (int i = startDaletouId; i <= max; i++) {
            DaletouBo daletouBo = downLoadDaletouHistory(i);
            if (null != daletouBo) {
                result.add(daletouBo);
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
    public DaletouBo downLoadDaletouHistory(Integer daletouId) {
        String url = "http://kaijiang.500.com/shtml/dlt/" + String.format("%05d", daletouId) + ".shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> result = new ParseUrl().getParseContents(url, css);

        DaletouBo daletouBo = null;
        try {
            daletouBo = new DaletouBo(daletouId, result);
        } catch (Exception e) {
            logger.error("download daletouBo is error! id:" + daletouId);
        }
        return daletouBo;
    }

    /**
     * 下载所有的大乐透
     *
     * @return 大乐透列表
     */
    @Override
    public List<DaletouBo> downLoadAllDaletouHistory() {
        int start = 2007;//从07年开始有大乐透
        int endYear = TimeUtil.getCurrentYear();

        List<DaletouBo> result = new ArrayList<DaletouBo>();
        for (; start <= endYear; start++) {
            List<DaletouBo> daletouBoEntities = downLoadNewDaletouHistory((start % 2000) * 1000 + 1);
            result.addAll(daletouBoEntities);
        }


        return result;
    }
}
