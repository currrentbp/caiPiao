package com.currentbp.shuangseqiu.service;

import com.currentbp.shuangseqiu.ShuangseqiuEntity;
import com.currentbp.util.ParseUrl;
import com.currentbp.util.all.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 加载双色球
 * 1、从网站上下载
 * 2、从本地加载
 *
 * @author current_bp
 * @createTime 20170916
 */
public class LoadShuangseqiu {
    private final static Logger logger = LoggerFactory.getLogger(LoadShuangseqiu.class);
    //本地历史数据的文件路径
    private String path = "E:\\ws\\idea_ws\\caiPiao\\20170913\\caiPiao\\src\\main\\resources\\shuangseqiu\\shuangseqiu_history.txt";
    /*
    双色球每年最多154期，
    从03年开始的
    每期的期号格式xxyyy：xx（年的后两位），yyy（从001累加到154）
     */

    /**
     * 加载本地的历史数据
     *
     * @param path 历史数据路径
     * @return 双色球的列表:id从小到大
     */
    public List<ShuangseqiuEntity> loadAllShuangseqiuFromLocal(String path) {
        List<String> fileContents = null;//StreamUtil.readFile(CheckUtil.isEmpty(path) ? this.path : path);
        List<ShuangseqiuEntity> shuangseqiuEntities = new ArrayList<ShuangseqiuEntity>();
        for (String fileContent : fileContents) {
            ShuangseqiuEntity shuangseqiuEntity = new ShuangseqiuEntity(fileContent);
            shuangseqiuEntities.add(shuangseqiuEntity);
        }
        return shuangseqiuEntities;
    }

    /**
     * 获取所有的双色球
     *
     * @return 所有的双色球
     */
    public List<ShuangseqiuEntity> loadAllShuangseqiuFromWeb() {
        List<ShuangseqiuEntity> result = new ArrayList<ShuangseqiuEntity>();
        int year = new Date().getYear() + 1900;
        for (int index = 2003; index <= year; index++) {
            String start = ("" + index).substring(2) + "001";
            String end = ("" + index).substring(2) + "154";
            List<ShuangseqiuEntity> someShuangseqius = loadShuangseqiuFromTo(Integer.parseInt(start), Integer.parseInt(end));
            result.addAll(someShuangseqius);
        }
        return result;
    }

    /**
     * 从网络中获取数据（下面两个参数必须有一个）
     *
     * @param from 起始点
     * @param to   终止点
     * @return 双色球的列表
     */
    public List<ShuangseqiuEntity> loadShuangseqiuFromTo(Integer from, Integer to) {
        if (CheckUtil.isEmpty(to)) {
            to = from + 154;
        }
        if (CheckUtil.isEmpty(from)) {
            from = to - 154;
        }
        List<ShuangseqiuEntity> shuangseqiuEntities = new ArrayList<ShuangseqiuEntity>();
        for (int i = from; i <= to; i++) {
            ShuangseqiuEntity shuangseqiuEntity = loadShuangseqiuOne(i);
            if (CheckUtil.isNotEmpty(shuangseqiuEntity)) {
                shuangseqiuEntities.add(shuangseqiuEntity);
            }
        }
        return shuangseqiuEntities;
    }

    private ShuangseqiuEntity loadShuangseqiuOne(int index) {
        String url = "http://kaijiang.500.com/shtml/ssq/" + String.format("%05d", index) + ".shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> result = new ParseUrl().getParseContents(url, css);
        logger.info("===>result :" + result);
        List<Integer> list = strings2Integers(result);
        if (CheckUtil.isEmpty(list)) {
            return null;
        }
        return new ShuangseqiuEntity(index, list);
    }


    //===========      private second            ============================//
    private List<Integer> strings2Integers(List<String> strings) {
        if (null == strings) {
            return null;
        }

        List<Integer> result = new ArrayList<Integer>();
        for (String string : strings) {
            try {
                result.add(Integer.parseInt(string));
            } catch (Exception e) {
                logger.error("===>string to integer error: value:" + string, e);
            }
        }
        return result;
    }
}
