package com.currentbp.util;

import com.currentbp.daletou.bo.entity.DaletouConstant;
import com.currentbp.daletou.entity.Daletou;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaletouUtil {

    public static List<Integer> getAllReds(List<Daletou> oldDaletous) {
        if (CollectionUtils.isEmpty(oldDaletous)) {
            return new ArrayList<>();
        }

        Set<Integer> result = new HashSet<>();
        oldDaletous.forEach(daletou -> {
            result.add(daletou.getRed1());
            result.add(daletou.getRed2());
            result.add(daletou.getRed3());
            result.add(daletou.getRed4());
            result.add(daletou.getRed5());
        });
        return new ArrayList<>(result);
    }

    public static List<Integer> getAllBlues(List<Daletou> oldDaletous) {
        if (CollectionUtils.isEmpty(oldDaletous)) {
            return new ArrayList<>();
        }

        Set<Integer> result = new HashSet<>();
        oldDaletous.forEach(daletou -> {
            result.add(daletou.getBlue1());
            result.add(daletou.getBlue2());
        });
        return new ArrayList<>(result);
    }

    public static List<Integer> getRemainBlues(List<Integer> oldBlues) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> olds = Sets.newHashSet(oldBlues);
        for (int i = 1; i <= DaletouConstant.BLUE_MAX_NUM; i++) {
            if (!olds.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public static List<Integer> getRemainReds(List<Integer> oldReds) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> olds = Sets.newHashSet(oldReds);
        for (int i = 1; i <= DaletouConstant.RED_MAX_NUM; i++) {
            if (!olds.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }
}
