package com.currentbp.daletou.service.impl;

import com.currentbp.constant.WinType;
import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.condition.DaletouCondition;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.DaletouService;
import com.currentbp.util.all.CollectionCommonUtil;
import com.currentbp.vo.Win;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author current_bp
 * @createTime 20180418
 */
@Service
public class DaletouServiceImpl implements DaletouService {
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

    @Override
    public List<Win> isWin(List<Daletou> daletous) {
        List<Integer> daletouIds = CollectionCommonUtil.getFieldListByMethodName(daletous, "getId", Integer.class);
        List<Daletou> source = daletouDao.queryByIds(daletouIds);
        Map<Integer, Daletou> sourceMap = CollectionCommonUtil.getMapFromListByMethodName(source, "getId", Integer.class);

        List<Win> result = daletous.stream().map(daletou -> {
            return isWin(daletou, sourceMap.get(daletou.getId()));
        }).collect(Collectors.toList());
        return result;
    }


    //=====================            private function                  =====================================================

    /**
     * 判断是否中奖，以及中奖情况
     *
     * @param daletou 需要预测的大乐透
     * @param source  大乐透
     * @return 是否中奖
     */
    private Win isWin(Daletou daletou, Daletou source) {
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
        return win;
    }

    /**
     * 查询出相同的部分
     *
     * @param forecast 预测数据
     * @param source   源数据
     * @return 结果
     */
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
        switch (blues){
            case 0:
                switch (reds){
                    case 3:
                        return WinType.DaletouWinType.getValueByType();
                    case 4:
                        return WinType.DaletouWinType.FIVE;
                    case 5:
                        return WinType.DaletouWinType.THREE;
                        default:
                            break;
                }
                break;
            case 1:
                switch (reds){
                    case 3:
                        return WinType.DaletouWinType.SIX;
                    case 4:
                        return WinType.DaletouWinType.FIVE;
                    case 5:
                        return WinType.DaletouWinType.THREE;
                    default:
                        break;
                }
                break;
            case 2:
                switch (reds){
                    case 3:
                        return WinType.DaletouWinType.SIX;
                    case 4:
                        return WinType.DaletouWinType.FIVE;
                    case 5:
                        return WinType.DaletouWinType.THREE;
                    default:
                        break;
                }
                break;
            default:
                    break;
        }
        if (5 == reds && 2 == blues) {
            return WinType.DaletouWinType.ONE;
        } else if (5 == reds && 1 == blues) {
            return WinType.DaletouWinType.TWO;
        } else if ((5 == reds && 0 == blues) || (4 == reds && 2 == blues)) {
            return WinType.DaletouWinType.THREE;
        } else if ((4 == reds && 1 == blues) || (3 == reds || 2 == blues)) {
            return WinType.DaletouWinType.FOUR;
        } else if ((4 == reds && 0 == blues) || (3 == reds && 1 == blues) || (2 == reds && 2 == blues)) {
            return WinType.DaletouWinType.FIVE;
        } else if ((3 == reds && 0 == blues) || (1 == reds && 2 == blues) || (2 == reds && 1 == blues) || (0 == reds && 2 == blues)) {
            return WinType.DaletouWinType.SIX;
        }
        return WinType.DaletouWinType.ZERO;
    }


}
