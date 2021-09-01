package com.currentbp.daletou.service.v4;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.util.all.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baopan
 * @createTime 20210901
 */
@Service
public class CheckHistoryHasSameService {
    @Autowired
    private DaletouDao daletouDao;

    public void t1() {
        List<Daletou> daletous = daletouDao.queryAll();
        List<Boolean> counts = new ArrayList<>(1000000);
        /**
         * 找出5个相同的
         */
        int i=0;
        for (Daletou daletou : daletous) {
            counts.add(false);
            DaletouBo daletouBo = new DaletouBo(daletou);
            for (Daletou daletou1 : daletous) {
                if (daletou.getId().equals(daletou1.getId())) {
                    continue;
                }
                if(daletouBo.sameNum(new DaletouBo(daletou1))>=6){
                    counts.set(i,true);
//                    System.out.println("daletou:"+daletou.toString()+" theSameDaletou:"+daletou1.toString());
                    break;
                }
            }
            i++;
        }
        StringUtil.printObject(counts);
    }
}
