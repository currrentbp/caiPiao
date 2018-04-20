package com.currentbp.daletou.service.impl;

import com.currentbp.daletou.condition.DaletouCondition;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.DaletouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author current_bp
 * @createTime 20180418
 */
@Service
public class DaletouServiceImpl implements DaletouService{
    @Autowired
    private DaletouDao daletouDao;

    @Override
    public void insert(Daletou daletou) {
        daletouDao.insert(daletou);
    }

    @Override
    public List<Daletou> queryDaletouAll() {
        return daletouDao.queryAll();
    }

    @Override
    public List<Daletou> queryDaletouByCondition(DaletouCondition daletouCondition) {
        return daletouDao.queryByCondition(daletouCondition);
    }
}
