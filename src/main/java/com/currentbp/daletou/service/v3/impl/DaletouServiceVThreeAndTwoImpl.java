package com.currentbp.daletou.service.v3.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v3.DaletouServiceVThreeAndTwo;
import com.currentbp.util.DaletouUtil;
import com.currentbp.util.all.RandomUtil;
import com.currentbp.util.all.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DaletouServiceVThreeAndTwoImpl implements DaletouServiceVThreeAndTwo {

    private int diffNum = 50;
    @Autowired
    private DaletouDao daletouDao;
    private List<DaletouBo> allDaletouBos = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<Daletou> daletous = daletouDao.queryAll();
        allDaletouBos = daletous.stream().map(DaletouBo::new).collect(Collectors.toList());
    }

    @Override
    public List<Daletou> forecastV3_2(int daletouId, int count, List<Daletou> oldDaletous) {
        List<Integer> allReds = DaletouUtil.getAllReds(oldDaletous);
        List<Integer> allBlues = DaletouUtil.getAllBlues(oldDaletous);
        List<Integer> remainReds = DaletouUtil.getRemainReds(allReds);
        List<Integer> remainBlues = DaletouUtil.getRemainBlues(allBlues);

        combination2(0, 2, getArr(remainBlues));
        List<int[]> blueCombinations = combinationArr;
        combinationArr = new ArrayList<>();
        tmpArr = new ArrayList<>();
        combination2(0, 5, getArr(remainReds));
        List<int[]> redCombinations = combinationArr;

        List<Daletou> allForecastResult = new ArrayList<>();
        System.out.println("===>redCombinationCount:" + redCombinations.size() + " blueCombinationCount:" + blueCombinations.size());
        redCombinations.forEach(reds -> {
            blueCombinations.forEach(blues -> {
                Daletou daletou = new Daletou(daletouId, reds, blues);
                if (isDiff(daletou)) {
//                    StringUtil.printObject(daletou);
                    allForecastResult.add(daletou);
                }
            });
        });
        System.out.println("===>redCombinationCount:" + redCombinations.size() +
                " blueCombinationCount:" + blueCombinations.size() + " allForecastResultCount:" + allForecastResult.size());
        if (CollectionUtils.isEmpty(allForecastResult)) {
            return new ArrayList<>();
        }

        List<Daletou> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomNum = RandomUtil.getRandomNum(allForecastResult.size());
            Daletou daletou = allForecastResult.get(randomNum);
            allForecastResult.remove(randomNum);
            result.add(daletou);
        }
        return result;
    }

    private boolean isDiff(Daletou daletou) {
        DaletouBo daletouBo = new DaletouBo(daletou);
        for (int i = 0; i < diffNum; i++) {
            //过滤比现在要新的大乐透数据
            if (daletou.getId() <= allDaletouBos.get(i).getId()) {
                continue;
            }
            if (!(daletouBo.diffNum(allDaletouBos.get(i)) > 3)) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> tmpArr = new ArrayList<>();
    public static List<int[]> combinationArr = new ArrayList<>();

    public static void combination2(int index, int k, int[] arr) {
        if (k == 1) {
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                combinationArr.add(getArr(tmpArr));
                tmpArr.remove((Object) arr[i]);
            }
        } else if (k > 1) {
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]); //tmpArr都是临时性存储一下
                combination2(i + 1, k - 1, arr); //索引右移，内部循环，自然排除已经选择的元素
                tmpArr.remove((Object) arr[i]); //tmpArr因为是临时存储的，上一个组合找出后就该释放空间，存储下一个元素继续拼接组合了
            }
        } else {
            return;
        }
    }

    private static int[] getArr(List<Integer> tmpArr) {
        int[] result = new int[tmpArr.size()];
        for (int i = 0; i < tmpArr.size(); i++) {
            result[i] = tmpArr.get(i);
        }
        return result;
    }
}
