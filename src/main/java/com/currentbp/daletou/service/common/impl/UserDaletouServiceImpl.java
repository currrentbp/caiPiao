package com.currentbp.daletou.service.common.impl;

import com.currentbp.daletou.dao.UserDaletouDao;
import com.currentbp.daletou.entity.UserDaletou;
import com.currentbp.daletou.service.common.UserDaletouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author baopan
 * @createTime 20210413
 */
@Service("userDaletouService")
public class UserDaletouServiceImpl implements UserDaletouService {
    @Autowired
    private UserDaletouDao userDaletouDao;
    @Override
    public void insert(UserDaletou userDaletou) {
        userDaletouDao.insert(userDaletou);
    }
}
