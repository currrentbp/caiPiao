package com.currentbp.daletou.service.v3.impl;

import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v3.DaletouServiceVThree;
import com.currentbp.util.all.MathUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DaletouServiceVThreeImpl implements DaletouServiceVThree {
    @Override
    public List<Daletou> forecastV2(int daletouId, int count) {

        combination2(0,2,new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        List<int[]> blueCombinations = combinationArr;
        combinationArr = new ArrayList<>();
        tmpArr = new ArrayList<>();
        combination2(0,5,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
                20,21,22,23,24,25,26,27,28,29,30,31,32});
        List<int[]> redCombinations = combinationArr;



        return null;
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
