package com.currentbp.daletou.service.v4;

import com.currentbp.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckHistoryHasSameServiceTest extends BaseTest {
    @Autowired
    private CheckHistoryHasSameService checkHistoryHasSameService;

    @Test
    public void t1(){
        checkHistoryHasSameService.t1();
    }
}
