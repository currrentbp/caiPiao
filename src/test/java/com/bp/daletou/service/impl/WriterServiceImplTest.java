package com.bp.daletou.service.impl;

import com.bp.BaseTest;
import com.bp.daletou.service.WriterService;
import com.currentbp.util.all.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

public class WriterServiceImplTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(WriterServiceImplTest.class);

    @Test
    public void writeDaletouHistory2Local() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("resources/daletou/daletou_history.txt");
        Assert.notNull(resourceAsStream,"sssssss");
    }

}