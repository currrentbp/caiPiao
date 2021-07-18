package com.currentbp.daletou.service.common;

import com.currentbp.daletou.entity.Daletou;
import com.currentbp.vo.Win;

public interface WinService {
    Win isWin(Daletou daletou, Daletou source);
}
