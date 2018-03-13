package com.bp.daletou.service.impl;

import com.bp.common.entity.DaletouEntity;
import com.bp.daletou.service.WriterService;
import com.currentbp.util.all.StreamUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 写入服务实现
 *
 * @author current_bp
 * @createTime 20180311
 */
public class WriterServiceImpl implements WriterService {
    /**
     * 将大乐透的历史数据追加写入历史文件中
     *
     * @param daletouEntities 大乐透列表
     */
    public void writeDaletouHistory2Local(List<DaletouEntity> daletouEntities) {
        FileWriter fileWriter = StreamUtil.createFileWriter("/daletou/daletou_history.txt", true);
        try {
            fileWriter.write("1");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != fileWriter){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
