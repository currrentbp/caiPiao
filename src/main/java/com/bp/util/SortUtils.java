package com.bp.util;

import com.bp.shuangseqiu.ShuangseqiuEntity;
import com.currentbp.util.all.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/17.
 */
public class SortUtils {

    public static List<ShuangseqiuEntity> sortShuangseqiu(List<ShuangseqiuEntity> shuangseqiuEntities){
        if(CheckUtil.isEmpty(shuangseqiuEntities)){
            return null;
        }
        //todo not work
        List<Integer> ids = CollectionUtil.getFieldListByObjectList(shuangseqiuEntities,"id",Integer.class);
        Map<Integer, ShuangseqiuEntity> oldShuangseqius = CollectionUtil.getMapByList(shuangseqiuEntities,"id");

        List<Integer> newIds = new SortUtil().bubblSort(ids,false);
        List<ShuangseqiuEntity> result = new ArrayList<ShuangseqiuEntity>();
        for(Integer id : newIds){
            if(CheckUtil.isNotEmpty(oldShuangseqius.get(id))) {
                result.add(oldShuangseqius.get(id));
            }
        }
        return result;

    }
}
