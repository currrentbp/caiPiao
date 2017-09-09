package com.bp.daletou;

import com.alibaba.fastjson.JSON;
import com.bp.util.all.CheckUtil;
import com.bp.util.all.PropertiesUtil;
import com.bp.util.all.RandomUtil;
import com.bp.util.all.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 大乐透
 *
 * @author current_bp
 * @createTime 20170503
 */
public class Daletou {
    private final static Logger logger = LoggerFactory.getLogger(Daletou.class);

    private Map<String, DaletouEntity> localDaletouHistory = new HashMap<String, DaletouEntity>();
    //排序好的历史IDs:从小到大排序
    private List<Integer> sortDaletouHistoryIds = new ArrayList<Integer>();
    //需要下载的历史IDs
    private List<Integer> needLoadDaletouHistoryIds = new ArrayList<Integer>();
    //最近：红球和篮球的重复概率
    private float[] redAndBluePro = new float[2];
    //所有的分析结果
    private Map<String, String> daletouAnalysis = new HashMap<String, String>();

    /**
     * 主方法
     */
    public static void main(String[] arg) {
        Daletou daletou = new Daletou();
        daletou.initHistory();
        List<DaletouEntity> lists = daletou.predictDaletou();
        logger.info("===>lists:" + lists);
    }

    //==================      init       ===============================================================//
    /*
    1、加载本地的历史数据(daletou_history.txt)，下载最新的数据，并保存到daletou_history.txt文件中
    2、对daletou_history.txt文件中的数据进行分析，将没有分析的数据写入
    3、获取一个或者多个预测数据
    4、将历史预测数据进行分析，
     */

    /**
     * 更新历史数据，将最新的数据放入该文件
     */
    public void initHistory() {
        initReadDaletouHistory();
        initNewDaletou();
        initAnalysis();
    }


    /**
     * 初始化本地的历史数据
     */
    public void initReadDaletouHistory() {
        List<String> daletouHistory = StreamUtil.readFile(
                "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_history.txt");
        if (CheckUtil.isEmpty(daletouHistory)) {
            return;
        }
        //重新初始化
        localDaletouHistory = new HashMap<String, DaletouEntity>();
        sortDaletouHistoryIds = new ArrayList<Integer>();

        List<Integer> list = new ArrayList<Integer>();
        Integer[] temp = new Integer[daletouHistory.size()];
        for (int i = 0; i < daletouHistory.size(); i++) {
            String daletou = daletouHistory.get(i);
            try {//如果格式不正确导致错误，则忽略
                String[] daletous = daletou.split(":");
                String[] nums = daletous[1].split(";");
                String reds = nums[0];
                String blues = nums[1];
                String[] reds1 = reds.split(",");
                String[] blues1 = blues.split(",");

                DaletouEntity daletouEntity = new DaletouEntity();
                daletouEntity.setId(daletous[0]);
                daletouEntity.setBlue(getIntegerArraysByStringArrays(blues1));
                daletouEntity.setRed(getIntegerArraysByStringArrays(reds1));

                localDaletouHistory.put(daletous[0], daletouEntity);
                temp[i] = Integer.parseInt(daletous[0]);
            } catch (Exception e) {
                continue;
            }
        }

        Arrays.sort(temp);
        sortDaletouHistoryIds = Arrays.asList(temp);
    }

    /**
     * 下载新的大乐透数据
     */
    private void initNewDaletou() {
        DaletouRepoPageProcessor daletouRepoPageProcessor = new DaletouRepoPageProcessor();
        //下载数据并将数据写入文件
        daletouRepoPageProcessor.getNewDaletouSources(localDaletouHistory);
        //重新初始化本地数据
        initReadDaletouHistory();
    }

    /**
     * 初始化分析结果，将新添加的历史数据与前N个数据比较，看看结果类似度
     */
    private void initAnalysis() {
        int analysisNum = Integer.parseInt(PropertiesUtil.getInstance("com/bp/daletou/config").getValueByKey("analysis_num"));
        //对比
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        for (int i = 0; i < sortDaletouHistoryIds.size(); i++) {
            if (i < analysisNum) {//如果前面的历史数据不够，就不要参加计算
                concurrentLinkedQueue.add(sortDaletouHistoryIds.get(i));
            } else {//有足够的历史数据计算，则先计算，再删除顶数据，添加新数据
                //每N个数据一分析
                float[] analysisResults = analysisBeforeLike(getIntegerArrayByObjectArray(concurrentLinkedQueue.toArray()), sortDaletouHistoryIds.get(i));
                logger.info("id:" + sortDaletouHistoryIds.get(i) + " like:" + JSON.toJSONString(analysisResults));

                //写入文件中
                try {
                    StreamUtil.writeSomethingToFile("" + sortDaletouHistoryIds.get(i) + ":" + analysisResults[0] + "," + analysisResults[1] + "\n",
                            "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_analysis.txt",
                            true);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                concurrentLinkedQueue.remove();
                concurrentLinkedQueue.add(sortDaletouHistoryIds.get(i));
            }
        }
    }

    /**
     * 预测大乐透
     *
     * @return 预测结果
     */
    public List<DaletouEntity> predictDaletou() {
        List<DaletouEntity> result = new ArrayList<DaletouEntity>();
        int predictNum = Integer.parseInt(PropertiesUtil.getInstance("com/bp/daletou/config").getValueByKey("predict_num"));
        int analysisNum = Integer.parseInt(PropertiesUtil.getInstance("com/bp/daletou/config").getValueByKey("analysis_num"));
        //初始化当前的重复概率
        initCurrentPredict();

        int redNum = Math.round(redAndBluePro[0] * 5);
        int blueNum = Math.round(redAndBluePro[1] * 2);

        //获取前N期的数据
        List<DaletouEntity> beforeDaletouList = new ArrayList<DaletouEntity>();
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = sortDaletouHistoryIds.size() - 1; i >= sortDaletouHistoryIds.size() - analysisNum; i--) {
            beforeDaletouList.add(localDaletouHistory.get("" + sortDaletouHistoryIds.get(i)));
            ids.add(sortDaletouHistoryIds.get(i));
        }
        Integer maxId = Collections.max(ids);
        //预测N个数据
        for (int i = 0; i < predictNum; i++) {
            result.add(getOnePredictDaletou(maxId, beforeDaletouList, redNum, blueNum));
        }

        //写入预测文件中
        for (int i = 0; i < result.size(); i++) {
            try {
                StreamUtil.writeSomethingToFile("" + result.get(i) + "\n",
                        "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_forecast.txt",
                        true);
            } catch (Exception e) {
                logger.info("===>write into forecast file is error!! msg:" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 初始化：最近红球和篮球的重复的概率
     */
    private void initCurrentPredict() {
        int analysisNum = Integer.parseInt(PropertiesUtil.getInstance("com/bp/daletou/config").getValueByKey("analysis_num"));
        //获取所有的分析结果
        getAllDaletouAnalysis();
        //获取前N个分析结果
        List<Integer> befor = new ArrayList<Integer>();
        //获取最近N期的数据
        for (int i = sortDaletouHistoryIds.size() - 1; i >= sortDaletouHistoryIds.size() - analysisNum; i--) {
            befor.add(sortDaletouHistoryIds.get(i));
        }

        //写入redAndBluePro
        for (int i = 0; i < befor.size(); i++) {
            String k = daletouAnalysis.get("" + befor.get(i));
            if (CheckUtil.isEmpty(k)) {
                continue;
            }
            String[] nums = k.split(",");
            float red = Float.parseFloat(nums[0]);
            float blue = Float.parseFloat(nums[1]);
            redAndBluePro[0] = redAndBluePro[0] + red;
            redAndBluePro[1] = redAndBluePro[1] + blue;
        }
        redAndBluePro[0] = redAndBluePro[0] / analysisNum;
        redAndBluePro[1] = redAndBluePro[1] / analysisNum;
    }

    //======================   私有方法  ====================================//
    private Integer[] getIntegerArrayByObjectArray(Object[] sources) {
        if (null == sources || 0 == sources.length) {
            return null;
        }

        Integer[] result = new Integer[sources.length];

        for (int i = 0; i < sources.length; i++) {
            result[i] = Integer.parseInt(sources[i].toString());
        }

        return result;
    }

    /**
     * 转换格式
     *
     * @param nums 原数据类型
     * @return 新数据类型
     */
    private Integer[] getIntegerArraysByStringArrays(String[] nums) {
        Integer[] result = new Integer[nums.length];

        for (int i = 0; i < nums.length; i++) {
            try {
                int num = Integer.parseInt(nums[i]);
                result[i] = num;
            } catch (Exception e) {
                logger.info("===>getIntArraysByStringArrays: error!! msg:" + e.getMessage());
            }
        }

        return result;
    }

    /**
     * 计算给定的一些历史数据和index的数据的相似度
     *
     * @param historyList 给定的历史数据的列表
     * @param index       需要比较的数据
     * @return 第一个是红色的相似度，第二个是蓝色相似度
     */
    private float[] analysisBeforeLike(Integer[] historyList, Integer index) {
        float[] result = new float[2];

        DaletouEntity indexDaletou = localDaletouHistory.get("" + index);

        for (int i = 0; i < historyList.length; i++) {
            DaletouEntity daletouEntity = localDaletouHistory.get("" + historyList[i]);
            float[] eachLike = analysisEachLike(indexDaletou, daletouEntity);
            result[0] = result[0] + eachLike[0];
            result[1] = result[1] + eachLike[1];
        }

        result[0] = result[0] / historyList.length;
        result[1] = result[1] / historyList.length;

        return result;
    }


    /**
     * 计算当前数字与以前的数字的相似度
     *
     * @param indexDaletou  当前的dlt
     * @param daletouEntity 以前的dlt
     * @return 相似度
     */
    private float[] analysisEachLike(DaletouEntity indexDaletou, DaletouEntity daletouEntity) {
        float[] result = new float[2];

        //红色区
        int redCount = 0;
        for (int i = 0; i < indexDaletou.getRed().length; i++) {
            for (int j = 0; j < daletouEntity.getRed().length; j++) {
                if (indexDaletou.getRed()[i] == daletouEntity.getRed()[j]) {
                    redCount++;
                }
            }
        }
        //蓝色区
        int blueCount = 0;
        for (int i = 0; i < indexDaletou.getBlue().length; i++) {
            for (int j = 0; j < daletouEntity.getBlue().length; j++) {
                if (indexDaletou.getBlue()[i] == daletouEntity.getBlue()[j]) {
                    blueCount++;
                }
            }
        }

        result[0] = ((float) redCount) / 5;
        result[1] = ((float) blueCount) / 5;

        return result;
    }


    /**
     * 将daletou_analysis.txt中的分析结果写入内存
     */
    private void getAllDaletouAnalysis() {
        File sourceFile = new File(
                "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_analysis.txt");

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        String temp = null;
        try {
            is = new FileInputStream(sourceFile);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while ((temp = br.readLine()) != null) {
                String[] result = temp.split(":");
                daletouAnalysis.put(result[0], result[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (Exception e) {
            }
        }

    }


    /**
     * 获取一个分析后的大乐透
     *
     * @param maxId             当前的预期ID
     * @param beforeDaletouList 前N期大乐透数据
     * @param redNum            红球重复数
     * @param blueNum           篮球重复数
     * @return 分析后的大乐透
     */
    private DaletouEntity getOnePredictDaletou(Integer maxId, List<DaletouEntity> beforeDaletouList, int redNum, int blueNum) {
        DaletouEntity daletouEntity = new DaletouEntity();

        //历史数据
        List<Integer> reds = new ArrayList<Integer>();
        List<Integer> blues = new ArrayList<Integer>();
        for (DaletouEntity daletouEntity1 : beforeDaletouList) {
            for (int i = 0; i < 5; i++) {
                if (!reds.contains(daletouEntity1.getRed()[i])) {
                    reds.add(daletouEntity1.getRed()[i]);
                }
            }
            for (int i = 0; i < 2; i++) {
                if (!blues.contains(daletouEntity1.getBlue()[i])) {
                    blues.add(daletouEntity1.getBlue()[i]);
                }
            }
        }

        logger.info("===>oldReds:" + JSON.toJSONString(reds) + " oldBlues:" + JSON.toJSONString(blues));
        //随机一组重复的红球和篮球出来
        Integer[] historyReds = getRandomNums(reds, redNum);
        Integer[] historyBlues = getRandomNums(blues, blueNum);
        List<Integer> historyReds1 = Arrays.asList(historyReds);
        List<Integer> historyBlues1 = Arrays.asList(historyBlues);

        //随意一个大乐透出来
        List<Integer> otherReds = new ArrayList<Integer>();
        List<Integer> otherBlues = new ArrayList<Integer>();
        for (int i = 1; i <= 35; i++) {
            if (reds.contains(i)) {
                continue;//该数字已经在历史中存在
            }
            if (!historyReds1.contains(i)) {
                otherReds.add(i);
            }
        }
        for (int i = 1; i <= 12; i++) {
            if (blues.contains(i)) {
                continue;//该数字已经在历史中存在
            }
            if (!historyBlues1.contains(i)) {
                otherBlues.add(i);
            }
        }
        logger.info("===>otherReds:" + JSON.toJSONString(otherReds) + " otherBlues:" + JSON.toJSONString(otherBlues));

        Integer[] remainReds = getRandomNums(otherReds, 5 - redNum);
        Integer[] remainBlues = getRandomNums(otherBlues, 2 - blueNum);

        List<Integer> newReds = new ArrayList<Integer>();
        newReds.addAll(Arrays.asList(historyReds));
        newReds.addAll(Arrays.asList(remainReds));
        List<Integer> newBlues = new ArrayList<Integer>();
        newBlues.addAll(Arrays.asList(historyBlues));
        newBlues.addAll(Arrays.asList(remainBlues));


        Integer[] newReds2 = new Integer[newReds.size()];
        Integer[] newBlues2 = new Integer[newBlues.size()];

        for (int i = 0; i < newReds.size(); i++) {
            newReds2[i] = newReds.get(i);
        }
        for (int i = 0; i < newBlues.size(); i++) {
            newBlues2[i] = newBlues.get(i);
        }


        try {
            StreamUtil.writeSomethingToFile("" + (maxId + 1) + ":" +
                            "oldReds:" + JSON.toJSONString(reds) + " oldBlues:" + JSON.toJSONString(blues) + "\n" +
                            "otherReds:" + JSON.toJSONString(otherReds) + " otherBlues:" + JSON.toJSONString(otherBlues) + "\n",
                    "E:\\ws\\idea_ws\\myGenProject\\20161223_7\\myGenProject\\src\\main\\resources\\com.bp.daletou\\daletou_forecast_remain.txt",
                    true);
        } catch (Exception e) {
            logger.error("===>wirte into daletou_forecast_remain.txt error!! ", e);
        }

        daletouEntity.setId("" + (maxId + 1));
        daletouEntity.setRed(newReds2);
        daletouEntity.setBlue(newBlues2);

        return daletouEntity;
    }

    /**
     * 从指定的一堆数中获取指定长度的数组
     *
     * @param sourceNums 原数组
     * @param needSize   需要的长度
     * @return 结果
     */
    private Integer[] getRandomNums(List<Integer> sourceNums, int needSize) {
        Integer[] result = new Integer[needSize];
        for (int i = 0; i < needSize; i++) {
            int index = RandomUtil.getRandomNum(sourceNums.size());
            result[i] = sourceNums.get(index);
            sourceNums.remove(index);
        }
        return result;
    }

    // ====================     get and set       ===============================================//

    public Map<String, DaletouEntity> getLocalDaletouHistory() {
        return localDaletouHistory;
    }

    public void setLocalDaletouHistory(Map<String, DaletouEntity> localDaletouHistory) {
        this.localDaletouHistory = localDaletouHistory;
    }

    public List<Integer> getSortDaletouHistoryIds() {
        return sortDaletouHistoryIds;
    }

    public void setSortDaletouHistoryIds(List<Integer> sortDaletouHistoryIds) {
        this.sortDaletouHistoryIds = sortDaletouHistoryIds;
    }

    public List<Integer> getNeedLoadDaletouHistoryIds() {
        return needLoadDaletouHistoryIds;
    }

    public void setNeedLoadDaletouHistoryIds(List<Integer> needLoadDaletouHistoryIds) {
        this.needLoadDaletouHistoryIds = needLoadDaletouHistoryIds;
    }

    public float[] getRedAndBluePro() {
        return redAndBluePro;
    }

    public void setRedAndBluePro(float[] redAndBluePro) {
        this.redAndBluePro = redAndBluePro;
    }

    public Map<String, String> getDaletouAnalysis() {
        return daletouAnalysis;
    }

    public void setDaletouAnalysis(Map<String, String> daletouAnalysis) {
        this.daletouAnalysis = daletouAnalysis;
    }
}
