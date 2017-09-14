package com.bp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author current_bp
 * @createTime 20170913
 */
public class ParseUrlTest {
    private final static Logger logger = LoggerFactory.getLogger(ParseUrlTest.class);

    private ParseUrl parseUrl = new ParseUrl();

    @Test
    public void getNewDaletouSources() throws Exception {
        String url = "http://kaijiang.500.com/shtml/ssq/17106.shtml";
        String css = "table.kj_tablelist02 >tbody >tr>td>table>tbody>tr>td>div>ul>li";
        parseUrl.getCaipiaoHistory(url,css);
    }

}