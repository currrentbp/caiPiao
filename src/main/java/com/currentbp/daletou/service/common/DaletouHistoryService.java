package com.currentbp.daletou.service.common;

import com.currentbp.daletou.entity.Daletou;

public interface DaletouHistoryService {
    /**
     * 这个大乐透是否在历史中存在
     */
    boolean inHistory(Daletou daletou);
}
