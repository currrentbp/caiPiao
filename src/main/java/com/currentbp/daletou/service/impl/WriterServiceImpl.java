package com.currentbp.daletou.service.impl;

import com.currentbp.common.entity.DaletouEntity;
import com.currentbp.daletou.service.WriterService;
import com.currentbp.util.all.StreamUtil;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * 写入服务实现
 *
 * @author current_bp
 * @createTime 20180311
 */
@Service("writerService")
public class WriterServiceImpl implements WriterService {
    /**
     * 将大乐透的历史数据追加写入历史文件中
     *
     * @param daletouEntities 大乐透列表
     */
    @Override
    public void writeDaletouHistory2Local(List<DaletouEntity> daletouEntities) {
        FileWriter fileWriter = StreamUtil.createFileWriter("/daletou/daletou_history.txt", false, true);
        try {
            for (DaletouEntity daletouEntity : daletouEntities) {
                fileWriter.write(daletouEntity.toString()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
