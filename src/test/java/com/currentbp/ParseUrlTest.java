package com.currentbp;

import com.currentbp.util.ParseUrl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrlTest {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrlTest.class);

    private ParseUrl parseUrl = new ParseUrl();

    @Test
    public void getNewDaletouSources() throws Exception {
//        //shuangseqiu
//        String url = "http://kaijiang.500.com/shtml/ssq/07001.shtml";
//        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
//        List<String> parseContents = parseUrl.getParseContents(url, css);
//        System.out.println(parseContents);

        //daletou
        String url2 = "http://kaijiang.500.com/shtml/dlt/07001.shtml";
        String css2 = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        List<String> parseContents1 = parseUrl.getParseContents(url2, css2);
        System.out.println(parseContents1);
    }

}