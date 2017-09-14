package com.bp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 下载url对应页面内容
 *
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrl {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrl.class);

    //需要下载内容的url
    private  String url = "http://kaijiang.500.com/shtml/ssq/17106.shtml";
    private  String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div";




    public void getCaipiaoHistory(String url,String parseBy) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(parseBy);
            logger.info("===>elementss:"+elements.html());
        }catch (Exception e){
            logger.error("===>msg:"+e.getMessage(),e);
        }
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getUrl() {
        return url;
    }

    public String getCss() {
        return css;
    }
}
