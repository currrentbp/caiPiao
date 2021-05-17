package com.currentbp.daletou.service.common.impl;

import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.common.DaletouHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaletouHistoryServiceImpl implements DaletouHistoryService {
    @Autowired
    private DaletouDao daletouDao;

    @Override
    public boolean inHistory(Daletou daletou) {
        return daletouDao.queryByRedAndBlue(daletou);
    }
}
