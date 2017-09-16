package com.bp.shuangseqiu;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20180916
 */
public class LoadShuangseqiuTest {
    private final static Logger logger = LoggerFactory.getLogger(LoadShuangseqiuTest.class);

    private LoadShuangseqiu loadShuangseqiu = new LoadShuangseqiu();

    @Test
    public void loadAllShuangseqiuFromWeb() throws Exception {
        loadShuangseqiu.loadAllShuangseqiuFromWeb();
    }

    @Test
    public void loadShuangseqiuFromTo() throws Exception {
        List<ShuangseqiuEntity> shuangseqiuEntities = loadShuangseqiu.loadShuangseqiuFromTo(17106, null);
        logger.info("===>shuangseqiuEntities:"+ JSON.toJSONString(shuangseqiuEntities));
    }

}