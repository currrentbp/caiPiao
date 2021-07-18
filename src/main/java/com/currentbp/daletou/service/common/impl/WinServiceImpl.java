package com.currentbp.daletou.service.common.impl;

import com.currentbp.constant.WinType;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.common.WinService;
import com.currentbp.vo.Win;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("winService")
public class WinServiceImpl implements WinService {

    @Override
    public Win isWin(Daletou daletou, Daletou source) {
        DaletouBo daletouBo = new DaletouBo(daletou);
        DaletouBo sourceDaletouBo = new DaletouBo(source);

        List<Integer> reds = theSame(daletouBo.getRed(), sourceDaletouBo.getRed());
        List<Integer> blues = theSame(daletouBo.getBlue(), sourceDaletouBo.getBlue());
        Win win = new Win();
        win.setBlues(blues);
        win.setReds(reds);
        win.setRedWin(reds.size());
        win.setBlueWin(blues.size());
        WinType.DaletouWinType daletouWin = isDaletouWin(reds.size(), blues.size());
        win.setMsg(daletouWin.getValue());
        win.setWin(0 != daletouWin.getType());
        win.setSource(source);
        win.setWinType(daletouWin.getType());
        return win;
    }
    private List<Integer> theSame(List<Integer> forecast, List<Integer> source) {
        return forecast.stream().filter(f -> source.indexOf(f) >= 0).collect(Collectors.toList());
    }
    /**
     * 计算大乐透的中间类型
     *
     * @param reds  红球数
     * @param blues 蓝球数
     * @return 中奖结果
     */
    private WinType.DaletouWinType isDaletouWin(int reds, int blues) {
        boolean redsIsFive = 5 == reds;
        boolean redsIsFour = 4 == reds;
        boolean redsIsThree = 3 == reds;
        boolean bluesIsZero = 0 == blues;
        boolean bluesIsOne = 1 == blues;
        boolean bluesIsTwo = 2 == blues;
        boolean redsIsOne = 1 == reds;
        boolean redsIsTwo = 2 == reds;
        boolean redsIsZero = 0 == reds;
        if (redsIsFive && bluesIsTwo) {
            return WinType.DaletouWinType.ONE;
        } else if (redsIsFive && bluesIsOne) {
            return WinType.DaletouWinType.TWO;
        } else if ((redsIsFive && bluesIsZero) || (redsIsFour && bluesIsTwo)) {
            return WinType.DaletouWinType.THREE;
        } else if ((redsIsFour && bluesIsOne) || (redsIsThree || bluesIsTwo)) {
            return WinType.DaletouWinType.FOUR;
        } else if ((redsIsFour && bluesIsZero) || (redsIsThree && bluesIsOne) || (redsIsTwo && bluesIsTwo)) {
            return WinType.DaletouWinType.FIVE;
        } else if ((redsIsThree && bluesIsZero) || (redsIsOne && bluesIsTwo) || (redsIsTwo && bluesIsOne) || (redsIsZero && bluesIsTwo)) {
            return WinType.DaletouWinType.SIX;
        }
        return WinType.DaletouWinType.ZERO;
    }

}
