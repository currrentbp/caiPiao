package com.currentbp.daletou.schedule;


import com.currentbp.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DownLoadLastedDaletouScheduleTest extends BaseTest {
    @Autowired
    private DownLoadLastedDaletouSchedule downLoadLastedDaletouSchedule;

    @Test
    public void t1(){
        for(int i=0;i<20;i++)
        downLoadLastedDaletouSchedule.downloadDaletouTask();
    }
}
