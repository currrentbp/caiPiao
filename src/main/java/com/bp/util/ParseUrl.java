package com.bp.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 下载url对应页面内容
 *
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrl {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrl.class);


    /**
     * 根据指定的url获取指定css样式的列表值
     * @param url 指定的url
     * @param parseBy 指定的样式
     * @return 结果集
     */
    public List<String> getParseContents(String url, String parseBy) {
        List<String> result = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(parseBy);
            for (Element element : elements) {
                result.add(element.html());
            }
            logger.info("===>getCaipiaoHistory:" + result);
        } catch (Exception e) {
            logger.error("===>msg:" + e.getMessage(), e);
        }
        return result;
    }

}
