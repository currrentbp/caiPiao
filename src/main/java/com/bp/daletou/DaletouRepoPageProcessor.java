package com.bp.daletou;

import com.alibaba.fastjson.JSON;
import com.bp.util.all.CheckUtil;
import com.bp.util.all.StreamUtil;
import com.bp.util.all.StringUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * Created by issuser on 2017/5/23.
 */
public class DaletouRepoPageProcessor implements PageProcessor {

    //本地存在的数据
    Map<String, DaletouEntity> oldDaletous = new HashMap<String, DaletouEntity>();
    //抓取下来的源数据
    private Map<String, DaletouEntity> newDaletouSource = new HashMap<String, DaletouEntity>();
    //需要添加的数据
    private Map<String, DaletouEntity> needAddDaletous = new HashMap<String, DaletouEntity>();
    //排序好的历史IDs:从小到大排序
    private List<Integer> sortDaletouHistoryIds = new ArrayList<Integer>();


    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public static String array2String(Integer[] source) {
        StringBuilder result = new StringBuilder("");
        if (CheckUtil.isEmpty(source)) {
            return result.toString();
        }

        for (Object o : source) {
            result.append(o.toString() + ",");
        }

        return result.lastIndexOf(",") == result.length() - 1 ? result.deleteCharAt(result.lastIndexOf(",")).toString() : result.toString();
    }

    public static void main(String[] args) {
        DaletouRepoPageProcessor daletouRepoPageProcessor = new DaletouRepoPageProcessor();
        daletouRepoPageProcessor.getNewDaletouSources(null);
        System.out.println("===============");
    }

    public Map<String, DaletouEntity> getNewDaletouSources(Map<String, DaletouEntity> oldDaletous) {
        this.oldDaletous = oldDaletous;
        Spider.create(new DaletouRepoPageProcessor()).addUrl("http://baidu.lecai.com/lottery/draw/list/1/?agentId=5599").thread(5).run();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("===>needAddDaletous:" + JSON.toJSONString(needAddDaletous));
        return this.needAddDaletous;
    }

    public void process(Page page) {
        List<String> historys = page.getHtml().css("table.historylist > tbody > tr").all();
        List<Integer> temp = new ArrayList<Integer>();
        Daletou daletou = new Daletou();
        daletou.initReadDaletouHistory();
        oldDaletous = daletou.getLocalDaletouHistory();
        for (String history : historys) {
            String aLabel = StringUtil.getALabel(history).get(0);
            String id = StringUtil.getLabelContent(aLabel, "a").get(0);
            List<String> emList = StringUtil.getLabel(history, "em");
            List<String> rolls = new ArrayList<String>();
            for (int i = 0; i < emList.size(); i++) {
                rolls.add(StringUtil.getLabelContent(emList.get(i), "em").get(0));
            }
            System.out.println("===>id:" + id + " rolls:" + rolls);


            if (!oldDaletous.containsKey(id)) {//添加该条数据
                DaletouEntity daletouEntity = new DaletouEntity();
                daletouEntity.setId(id);
                String[] result1 = rolls.subList(0, 5).toArray(new String[5]);
                Integer[] result2 = new Integer[5];
                for (int i = 0; i < 5; i++) {
                    result2[i] = Integer.parseInt(result1[i]);
                }
                daletouEntity.setRed(result2);

                String[] result1_1 = rolls.subList(5, 7).toArray(new String[2]);
                Integer[] result2_1 = new Integer[2];
                for (int i = 0; i < 2; i++) {
                    result2_1[i] = Integer.parseInt(result1_1[i]);
                }
                daletouEntity.setBlue(result2_1);
                this.needAddDaletous.put(id, daletouEntity);
                temp.add(Integer.parseInt(id));
            }
        }
        Arrays.sort(temp.toArray());
        sortDaletouHistoryIds = temp;

        //将needAddDaletous写入文件
        for (Integer id : sortDaletouHistoryIds) {
            try {
                String reds = array2String(needAddDaletous.get("" + id).getRed());
                String blues = array2String(needAddDaletous.get("" + id).getBlue());
                String content = "" + id + ":" + reds + ";" + blues + "\n";
                System.out.println("===>content:" + content);
                StreamUtil.writeSomethingToFile(content,
                        "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_history.txt",
                        true);
            } catch (Exception e) {
                System.out.println("===>write into file error!! msg:" + e.getMessage());
            }
        }
    }

    public Site getSite() {
        return site;
    }

}
