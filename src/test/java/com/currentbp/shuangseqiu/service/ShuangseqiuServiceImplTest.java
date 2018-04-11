package com.currentbp.shuangseqiu.service;

import com.alibaba.fastjson.JSON;
import com.currentbp.shuangseqiu.ShuangseqiuEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author current_bp
 * @createTime 20171006
 */
public class ShuangseqiuServiceImplTest {
    private final static Logger logger = LoggerFactory.getLogger(ShuangseqiuServiceImplTest.class);

    @Test
    public void getRepeatNums() throws Exception {
        List<ShuangseqiuEntity> shuangseqiuEntities = new ArrayList<ShuangseqiuEntity>();
        for (int i = 0; i < 5; i++) {
            ShuangseqiuEntity shuangseqiuEntity = new ShuangseqiuEntity(true);
            shuangseqiuEntities.add(shuangseqiuEntity);
        }

        Map<Integer, Map<String, Map<Integer, Integer>>> repeatProblem = new ShuangseqiuServiceImpl().getRepeatNums(2, shuangseqiuEntities);
        logger.info("===>oldList:"+JSON.toJSONString(shuangseqiuEntities));
        logger.info("===>result:"+JSON.toJSONString(repeatProblem));
    }

    @Test
    public void getTotalRepeatProblem() throws Exception {
        List<ShuangseqiuEntity> shuangseqiuEntities = new ArrayList<ShuangseqiuEntity>();
        for (int i = 0; i < 7; i++) {
            ShuangseqiuEntity shuangseqiuEntity = new ShuangseqiuEntity(true);
            shuangseqiuEntities.add(shuangseqiuEntity);
        }

        Map<Integer, Map<String, Map<Integer, Integer>>> repeatProblem = new ShuangseqiuServiceImpl().getRepeatNums(2, shuangseqiuEntities);
        logger.info("===>oldList:"+JSON.toJSONString(shuangseqiuEntities));
        logger.info("===>result:"+JSON.toJSONString(repeatProblem));

        Map<Integer, Map<String, Integer>> totalRepeatProblem = new ShuangseqiuServiceImpl()
                .getTotalRepeatProblem(2, shuangseqiuEntities, repeatProblem);
        logger.info("===>totalRepeatProblem:"+JSON.toJSONString(totalRepeatProblem));

    }

}