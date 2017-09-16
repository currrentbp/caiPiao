package com.bp.shuangseqiu;

import com.bp.util.ParseUrl;

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

    /*
    双色球每年最多154期，
    从03年开始的
    每期的期号格式xxyyy：xx（年的后两位），yyy（从001累加到154）
     */

    public List<ShuangseqiuEntity> loadAllShuangseqiuFromWeb() {
        List<ShuangseqiuEntity> result = new ArrayList<ShuangseqiuEntity>();
        for (int index = 2003; index <= new Date().getYear(); index++) {
            String start = ("" + index).substring(2) + "001";
            String end = ("" + index).substring(2) + "154";
            List<ShuangseqiuEntity> someShuangseqius = loadShuangseqiuFromTo(Integer.parseInt(start), Integer.parseInt(end));
            result.addAll(someShuangseqius);
        }
        return result;
    }

    private List<ShuangseqiuEntity> loadShuangseqiuFromTo(Integer from, Integer to) {
        List<ShuangseqiuEntity> shuangseqiuEntities = new ArrayList<ShuangseqiuEntity>();
        for (int i = from; i <= to; i++) {
            shuangseqiuEntities.add(loadShuangseqiuOne(i));
        }
        return shuangseqiuEntities;
    }

    private ShuangseqiuEntity loadShuangseqiuOne(int index) {
        String url = "http://kaijiang.500.com/shtml/ssq/" + String.format("%05d", index) + ".shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> result = new ParseUrl().getParseContents(url, css);
        return null;
    }


}
