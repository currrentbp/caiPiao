package com.bp.shuangseqiu;

import com.currentbp.util.all.ListUtil;
import com.currentbp.util.all.StringUtil;
import java.util.*;

/**
 * 双色球实体
 *
 * @author current_bp
 * @createTime 20170909
 */
public class ShuangseqiuEntity {

    private Integer id;
    private Integer[] red = new Integer[6];
    private Integer blue = 0;
    private Integer[] blues = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    private List<Integer> reds = new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,
            19,20,21,22,23,24,25,26,27,28,29,30,31,32,33}));
    private static int index = 0;

    public ShuangseqiuEntity() {
    }
    public ShuangseqiuEntity(boolean random){
        if(random) {
            Date date = new Date();
            String time = "" + date.getTime()+""+(index++);
            this.id = Integer.parseInt(time.substring(8));
            this.blue = blues[new Random().nextInt(15)];
            for (int i = 0; i < 6; i++) {
                int index = new Random().nextInt(16 - i);
                this.red[i] = reds.get(index);
                reds.remove(index);
            }
        }
    }

    public ShuangseqiuEntity(int id, List<Integer> allNums) {
        this.id = id;
        for (int i = 0; i < 6; i++) {
            red[i] = allNums.get(i);
        }
        blue = allNums.get(6);
    }

    public ShuangseqiuEntity(String source) {
        this.id = Integer.parseInt(source.split(":")[0]);

        String allNumString = source.split(":")[1];
        String[] allNumStrings = allNumString.split(",|;");
        List<String> allNumList = Arrays.asList(allNumStrings);
        List<Integer> allNums = new ListUtil().stringList2IntegerList(allNumList);

        for (int i = 0; i < 6; i++) {
            red[i] = allNums.get(i);
        }
        blue = allNums.get(6);
    }

    private List<Integer> getAllNumsBySource(String source) {
        String allNumString = source.split(":")[1];
        String[] allNumStrings = allNumString.split(",|;");
        List<String> allNumList = Arrays.asList(allNumStrings);
        return new ListUtil().stringList2IntegerList(allNumList);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[] getRed() {
        return red;
    }

    public void setRed(Integer[] red) {
        this.red = red;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShuangseqiuEntity that = (ShuangseqiuEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "" + this.id + ":" + StringUtil.array2String(this.red) + ";" + this.blue;
    }
}
