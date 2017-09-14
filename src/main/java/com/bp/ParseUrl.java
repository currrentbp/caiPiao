package com.bp;

import com.alibaba.fastjson.JSON;
import com.bp.daletou.Daletou;
import com.bp.daletou.DaletouEntity;
import com.bp.daletou.DaletouRepoPageProcessor;
import com.bp.util.all.CheckUtil;
import com.bp.util.all.StreamUtil;
import com.bp.util.all.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * 下载url对应页面内容
 *
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrl implements PageProcessor {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrl.class);

    //需要下载内容的url
    private  String url = "http://kaijiang.500.com/shtml/ssq/17106.shtml";
    private  String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div";
    private final Map<String,Object> param = new HashMap<String, Object>();
    {
        param.put("url",url);
        param.put("css",css);
        param.put("result",new StringBuffer());
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);


    public void getNewDaletouSources() {
        Spider.create(new ParseUrl()).addUrl(this.param.get("url").toString()).thread(5).run();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void process(Page page) {
        List<String> historys = page.getHtml().css(this.param.get("css").toString()).all();
        logger.info("===>historys:"+JSON.toJSONString(historys));
        ((StringBuffer)param.get("result")).append(historys.get(0));
    }

    public Site getSite() {
        return site;
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

    public Map<String, Object> getParam() {
        return param;
    }
}
