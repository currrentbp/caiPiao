package com.currentbp.daletou.analysis;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.util.all.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryRepeatAnalysis {
    @Autowired
    private DaletouDao daletouDao;
    private int diffMaxLength = 6;

    public void diffLength() {
        List<Daletou> daletous = daletouDao.queryAll();
        List<DaletouBo> daletouBos = daletous.stream().map(DaletouBo::new).collect(Collectors.toList());
        List<Integer> maxLengths = new ArrayList<>();

        for (int currentIndex = 0; currentIndex < daletouBos.size(); currentIndex++) {
            int maxLen = 0;
            for (int nextIndex = currentIndex + 1; nextIndex < daletouBos.size(); nextIndex++) {
                if (diff(daletouBos.get(currentIndex), daletouBos.get(nextIndex))) {
                    maxLen++;
                }
            }
            maxLengths.add(maxLen);
        }

        StringUtil.printObject(maxLengths);
    }

    private boolean diff(DaletouBo daletouBo, DaletouBo daletouBo1) {
        return daletouBo.diffNum(daletouBo1) > diffMaxLength;
    }
}
