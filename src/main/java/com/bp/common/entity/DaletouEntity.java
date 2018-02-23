package com.bp.common.entity;

import com.currentbp.util.all.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 大乐透号码
 *
 * @author current_bp
 * @createTime 20170503
 */
public class DaletouEntity {
    private Integer id;
    private List<Integer> red = new ArrayList<Integer>(5);
    private List<Integer> blue = new ArrayList<Integer>(2);

    public DaletouEntity() {
    }

    public DaletouEntity(Integer id, List<String> daletouStrings) {
        this.id = id;
        for (int i = 0; i < daletouStrings.size(); i++) {
            if (i < 5) {
                red.add(Integer.parseInt(daletouStrings.get(i)));
            } else {
                blue.add(Integer.parseInt(daletouStrings.get(i)));
            }
        }
    }

    public DaletouEntity(String source) {
        //17059:8,11,13,15,17;3,10
        List<String> strings = StringUtil.stringToList(source, ":");
        this.id = Integer.parseInt(strings.get(0));
        String redsAndBlues = strings.get(1);
        List<String> strings1 = StringUtil.stringToList(redsAndBlues, ";");
        String reds = strings1.get(0);
        String blues = strings1.get(1);
        List<String> reds1 = StringUtil.stringToList(reds);
        List<String> blues1 = StringUtil.stringToList(blues);
        for (String red : reds1) {
            this.red.add(Integer.parseInt(red));
        }
        for (String blue : blues1) {
            this.blue.add(Integer.parseInt(blue));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getRed() {
        return red;
    }

    public void setRed(List<Integer> red) {
        this.red = red;
    }

    public List<Integer> getBlue() {
        return blue;
    }

    public void setBlue(List<Integer> blue) {
        this.blue = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DaletouEntity that = (DaletouEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        sb.append(id + ":" +
                StringUtil.list2String(red) + " " +
                StringUtil.list2String(blue));
        return sb.toString();
    }
}
