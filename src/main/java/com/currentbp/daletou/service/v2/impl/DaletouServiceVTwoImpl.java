package com.currentbp.daletou.service.v2.impl;

import com.currentbp.daletou.bo.entity.DaletouConstant;
import com.currentbp.daletou.entity.Daletou;
import com.currentbp.daletou.service.v2.DaletouServiceVTwo;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author baopan
 * @createTime 20210411
 */
@Service
public class DaletouServiceVTwoImpl implements DaletouServiceVTwo {

    @Override
    public List<Daletou> forecastV2(int count, List<Daletou> oldDaletous) {
        List<Integer> oldReds = getOldReds(oldDaletous);
        List<Integer> oldBlues = getOldBlues(oldDaletous);
        List<Daletou> newDaletous = forecast(count, oldReds, oldBlues);
        return newDaletous;
    }

    private List<Daletou> forecast(int count, List<Integer> oldReds, List<Integer> oldBlues) {
        List<Integer> remainReds = getRemainReds(oldReds);
        List<Integer> remainBlues = getRemainBlues(oldBlues);
        return null;
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
