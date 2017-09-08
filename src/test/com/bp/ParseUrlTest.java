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
        parseUrl.setUrl("");
        parseUrl.setCss("");
        parseUrl.getNewDaletouSources();
    }

}