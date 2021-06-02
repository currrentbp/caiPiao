package com.currentbp.daletou.service.v3.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v3.DaletouServiceVThree;
import com.currentbp.util.all.MathUtil;
import com.currentbp.util.all.RandomUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DaletouServiceVThreeImpl implements DaletouServiceVThree {
    private int diffNum = 500;
    @Autowired
    private DaletouDao daletouDao;
    private List<DaletouBo> allDaletouBos = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<Daletou> daletous = daletouDao.queryAll();
        allDaletouBos = daletous.stream().map(DaletouBo::new).collect(Collectors.toList());
    }



    @Override
    public List<Daletou> forecastV3(int daletouId, int count) {

        combination2(0,2,new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        List<int[]> blueCombinations = combinationArr;
        combinationArr = new ArrayList<>();
        tmpArr = new ArrayList<>();
        combination2(0,5,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
                20,21,22,23,24,25,26,27,28,29,30,31,32});
        List<int[]> redCombinations = combinationArr;

        List<Daletou> allForecastResult  = new ArrayList<>();
        redCombinations.forEach(reds->{
            blueCombinations.forEach(blues->{
                Daletou daletou = new Daletou(daletouId, reds, blues);
                if(isDiff(daletou)){
                    allForecastResult.add(daletou);
                }
            });
        });
        if(CollectionUtils.isEmpty(allForecastResult)){
            return new ArrayList<>();
        }

        List<Daletou> result = new ArrayList<>();
        for(int i=0;i<count;i++){
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
            if (!(daletouBo.diffNum(allDaletouBos.get(i)) > 6)) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> tmpArr = new ArrayList<>();
    public static List<int[]> combinationArr = new ArrayList<>();
    public static void combination2(int index,int k,int []arr) {
        if(k == 1){
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                combinationArr.add(getArr(tmpArr));
                tmpArr.remove((Object)arr[i]);
            }
        }else if(k > 1){
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]); //tmpArr都是临时性存储一下
                combination2(i + 1,k - 1, arr); //索引右移，内部循环，自然排除已经选择的元素
                tmpArr.remove((Object)arr[i]); //tmpArr因为是临时存储的，上一个组合找出后就该释放空间，存储下一个元素继续拼接组合了
            }
        }else{
            return ;
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
