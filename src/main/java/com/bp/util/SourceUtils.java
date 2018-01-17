package com.bp.util;


import com.currentbp.util.all.Assert;
import com.currentbp.util.all.StreamUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于解析文件中的内容
 *
 * @author current_bp
 * @createTime 20180115
 */
public class SourceUtils {
    private final static Logger logger = LoggerFactory.getLogger(SourceUtils.class);

    private final static String PROJECT_PATH = System.getProperty("user.dir");

    private final static String PROJECT_SOURCES_PATH = "/src/main/resources/";

    /**
     * 读取一个文件中的所有内容到列表中
     *
     * @param path 文件路径
     * @return 内容列表
     */
    public static List<String> getListBySource(String path) {
        path = PROJECT_PATH + PROJECT_SOURCES_PATH + path;
        logger.info(path);
        Assert.isTrue(StreamUtil.isFile(path), "文件路径错误");
        InputStream is = Class.class.getResourceAsStream(path);
        List<String> result = new ArrayList<String>();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;
        while (true) {
            try {
                if (null != (temp = br.readLine())) {
                    result.add(temp);
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Test
    public void t1() {
        //TODO error
        logger.info(SourceUtils.getListBySource("shuangseqiu/shuangseqiu_history.txt").toString());
    }
}
