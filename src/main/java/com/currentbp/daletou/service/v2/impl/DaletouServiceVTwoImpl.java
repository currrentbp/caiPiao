package com.currentbp.daletou.service.v2.impl;

import com.currentbp.daletou.bo.entity.DaletouBo;
import com.currentbp.daletou.bo.entity.DaletouConstant;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.common.DaletouHistoryService;
import com.currentbp.daletou.service.v2.DaletouServiceVTwo;
import com.currentbp.util.all.RandomUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author baopan
 * @createTime 20210411
 */
@Service
public class DaletouServiceVTwoImpl implements DaletouServiceVTwo {
    @Autowired
    private DaletouHistoryService daletouHistoryService;

    @Override
    public List<Daletou> forecastV2(int count, List<Daletou> oldDaletous) {
        List<Integer> oldReds = getOldReds(oldDaletous);
        List<Integer> oldBlues = getOldBlues(oldDaletous);
        return forecast(count, oldReds, oldBlues);
    }

    private List<Daletou> forecast(int count, List<Integer> oldReds, List<Integer> oldBlues) {
        List<Integer> remainReds = getRemainReds(oldReds);
        List<Integer> remainBlues = getRemainBlues(oldBlues);
        if (remainReds.size() < DaletouConstant.RED_CHOICE_NUM || remainBlues.size() < DaletouConstant.BLUE_CHOICE_NUM) {
            return new ArrayList<>();
        }
        List<Daletou> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Daletou each = getEach(remainReds, remainBlues);
            boolean isSame = hasSame(result, each);
            if (isSame) {
                i--;
                continue;
            }
            boolean inHistory = daletouHistoryService.inHistory(each);
            if (inHistory) {
                i--;
                continue;
            }
            result.add(each);
        }
        return result;
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
