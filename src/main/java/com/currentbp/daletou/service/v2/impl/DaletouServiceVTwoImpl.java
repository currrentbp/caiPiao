package com.currentbp.daletou.service.v2.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.DaletouConstant;
import com.currentbp.daletou.dao.DaletouDao;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.common.DaletouHistoryService;
import com.currentbp.daletou.service.v2.DaletouServiceVTwo;
import com.currentbp.util.all.MathUtil;
import com.currentbp.util.all.RandomUtil;
import com.currentbp.util.all.StringUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author baopan
 * @createTime 20210411
 */
@Service
public class DaletouServiceVTwoImpl implements DaletouServiceVTwo {
    @Autowired
    private DaletouHistoryService daletouHistoryService;
    @Autowired
    private DaletouDao daletouDao;
    private int diffNum = 500;

    private List<DaletouBo> allDaletouBos = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<Daletou> daletous = daletouDao.queryAll();
        allDaletouBos = daletous.stream().map(DaletouBo::new).collect(Collectors.toList());
    }


    @Override
    public List<Daletou> forecastV2(int daletouId, int count, List<Daletou> oldDaletous) {
        List<Integer> oldReds = getOldReds(oldDaletous);
        List<Integer> oldBlues = getOldBlues(oldDaletous);
        return forecast(daletouId, count, oldReds, oldBlues);
    }

    private List<Daletou> forecast(int daletouId, int count, List<Integer> oldReds, List<Integer> oldBlues) {
        List<Integer> remainReds = getRemainReds(oldReds);
        List<Integer> remainBlues = getRemainBlues(oldBlues);
        if (remainReds.size() < DaletouConstant.RED_CHOICE_NUM || remainBlues.size() < DaletouConstant.BLUE_CHOICE_NUM) {
            return new ArrayList<>();
        }
        List<Daletou> result = new ArrayList<>();
        List<Daletou> each2 = getEach2(remainReds, remainBlues);

        each2.forEach(e->{
            e.setId(daletouId);
            boolean isSame = hasSame(result, e);
            if(isSame){
                return;
            }
            boolean inHistory = daletouHistoryService.inHistory(e);
            if (inHistory) {
                return;
            }
            if (!isDiff(e)) {
                return;
            }
            result.add(e);
        });
        System.out.println("===>allResultCount:"+result.size());

        List<Daletou> temp = new ArrayList<>();
        for(int i=0;i<count;i++){
            if(result.size()==0){
                return new ArrayList<>();
            }
            int randomNum = RandomUtil.getRandomNum(result.size()-1);
            temp.add(result.get(randomNum));
            result.remove(randomNum);
        }

        return temp;
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

    private boolean hasSame(List<Daletou> allDaletous, Daletou currentDaletou) {
        for (Daletou daletou : allDaletous) {
            if (daletou.getRed1().equals(currentDaletou.getRed1()) &&
                    daletou.getRed2().equals(currentDaletou.getRed2()) &&
                    daletou.getRed3().equals(currentDaletou.getRed3()) &&
                    daletou.getRed4().equals(currentDaletou.getRed4()) &&
                    daletou.getRed5().equals(currentDaletou.getRed5()) &&
                    daletou.getBlue1().equals(currentDaletou.getBlue1()) &&
                    daletou.getBlue2().equals(currentDaletou.getBlue2())) {
                return true;
            }
        }
        return false;
    }

    private Daletou getEach(List<Integer> remainReds, List<Integer> remainBlues) {
        Daletou daletou = new Daletou();
        Set<Integer> contains = new HashSet<>();
        while (DaletouConstant.RED_CHOICE_NUM != contains.size()) {
            contains.add(remainReds.get(RandomUtil.getRandomNum(remainReds.size())));
        }
        List<Integer> reds = new ArrayList<>(contains);
        daletou.setRed1(reds.get(0));
        daletou.setRed2(reds.get(1));
        daletou.setRed3(reds.get(2));
        daletou.setRed4(reds.get(3));
        daletou.setRed5(reds.get(4));

        contains.clear();
        while (DaletouConstant.BLUE_CHOICE_NUM != contains.size()) {
            contains.add(remainBlues.get(RandomUtil.getRandomNum(remainBlues.size())));
        }
        List<Integer> blues = new ArrayList<>(contains);
        daletou.setBlue1(blues.get(0));
        daletou.setBlue2(blues.get(1));

        return daletou;
    }

//    @Test
//    public void t1() {
//        List<Integer> remainReds = new ArrayList<>();
//        List<Integer> remainBlues = new ArrayList<>();
//        remainReds.add(1);
//        remainReds.add(2);
//        remainReds.add(3);
//        remainReds.add(4);
//        remainReds.add(5);
//        remainReds.add(6);
//        remainReds.add(7);
//        remainBlues.add(1);
//        remainBlues.add(2);
//        remainBlues.add(3);
//        remainBlues.add(4);
//        getEach2(remainReds, remainBlues);
//    }


    private List<Daletou> getEach2(List<Integer> remainReds, List<Integer> remainBlues) {
        List<Daletou> daletous = new ArrayList<>();
        int[] redIndexs = new int[]{0, 1, 2, 3, 4};
        int[] blueIndex = new int[]{0, 1};

        List<List<Integer>> reds = getSource(remainReds, redIndexs, 5);
        List<List<Integer>> blues = getSource(remainBlues, blueIndex, 2);

        reds.forEach(red->{
            blues.forEach(blue->{
                daletous.add(new Daletou(null,red,blue));
            });
        });

        return daletous;
    }

    private List<List<Integer>> getSource(List<Integer> sources, int[] redIndexs, int sum) {
        List<List<Integer>> result = new ArrayList<>();

        int count = 0;
        while (true) {
            result.add(getEachReds(sources, redIndexs));
            if (complit(redIndexs, sources.size())) {
                break;
            }
            redIndexs = changeRedIndexs(redIndexs, sources, sum);
            if (null == redIndexs) {
                break;
            }
        }

        count = result.size();
        System.out.println("===>redscount:" + count);
        return result;
    }

    private int[] changeRedIndexs(int[] redIndexs, List<Integer> sources, int sum) {
        int size = sources.size() - 1;
        int redSum = 0;
        for (int i = redIndexs.length - 1; i >= 0; i--) {
            if (redIndexs[i] + (redIndexs.length - i - 1) == size) {
                redSum++;
                continue;
            } else {
                redIndexs[i] = redIndexs[i] + 1;
                break;
            }
        }
        if (sum == redSum) {
            return null;
        }
        return redIndexs;
    }

    private List<Integer> getEachReds(List<Integer> sources, int[] redIndexs) {
        List<Integer> result = new ArrayList<>();
        for (int redIndex : redIndexs) {
            result.add(sources.get(redIndex));
        }
        return result;
    }

    private boolean complit(int[] indexs, int size) {
        if (null == indexs) {
            return true;
        }


        if (indexs.length == 5) {
            return indexs[4] == size - 1
                    && indexs[4] == indexs[3] + 1
                    && indexs[3] == indexs[2] + 1
                    && indexs[2] == indexs[1] + 1
                    && indexs[1] == indexs[0] + 1;
        } else {
            return indexs[1] == size - 1 && indexs[1] == indexs[0] + 1;
        }
    }

    private List<Integer> getRemainBlues(List<Integer> oldBlues) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> olds = Sets.newHashSet(oldBlues);
        for (int i = 1; i <= DaletouConstant.BLUE_MAX_NUM; i++) {
            if (!olds.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private List<Integer> getRemainReds(List<Integer> oldReds) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> olds = Sets.newHashSet(oldReds);
        for (int i = 1; i <= DaletouConstant.RED_MAX_NUM; i++) {
            if (!olds.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private List<Integer> getOldBlues(List<Daletou> oldDaletous) {
        if (CollectionUtils.isEmpty(oldDaletous)) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        oldDaletous.forEach(daletou -> {
            result.add(daletou.getBlue1());
            result.add(daletou.getBlue2());
        });
        return result;
    }

    private List<Integer> getOldReds(List<Daletou> oldDaletous) {
        if (CollectionUtils.isEmpty(oldDaletous)) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        oldDaletous.forEach(daletou -> {
            result.add(daletou.getRed1());
            result.add(daletou.getRed2());
            result.add(daletou.getRed3());
            result.add(daletou.getRed4());
            result.add(daletou.getRed5());
        });
        return result;
    }
}
