package com.currentbp;

import com.currentbp.util.ParseUrl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrlTest {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrlTest.class);

    private ParseUrl parseUrl = new ParseUrl();

    @Test
    public void getNewDaletouSources() throws Exception {
        //shuangseqiu
        String url = "http://kaijiang.500.com/shtml/ssq/17106.shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        parseUrl.getParseContents(url,css);

        //daletou
        String url2 = "http://kaijiang.500.com/shtml/dlt/17093.shtml";
        String css2 = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        parseUrl.getParseContents(url2,css2);
    }

}