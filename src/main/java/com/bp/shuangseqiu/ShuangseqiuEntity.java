package com.bp.shuangseqiu;

import com.bp.util.all.ListUtil;
import com.bp.util.all.StringUtil;
import java.util.Arrays;
import java.util.List;

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

    public ShuangseqiuEntity() {
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
