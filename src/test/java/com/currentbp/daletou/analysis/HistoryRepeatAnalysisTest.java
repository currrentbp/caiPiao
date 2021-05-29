package com.currentbp.daletou.analysis;

import com.currentbp.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HistoryRepeatAnalysisTest extends BaseTest {
    @Autowired
    private HistoryRepeatAnalysis historyRepeatAnalysis;

    @Test
    public void t1(){
        historyRepeatAnalysis.diffLength();
    }
}