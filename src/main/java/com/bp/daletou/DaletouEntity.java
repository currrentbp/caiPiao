package com.bp.daletou;

import com.bp.util.all.StreamUtil;

import java.util.List;

/**
 * 大乐透号码
 *
 * @author current_bp
 * @createTime 20170503
 */
public class DaletouEntity {
    private String id;
    private Integer[] red = new Integer[5];
    private Integer[] blue = new Integer[2];

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer[] getRed() {
        return red;
    }

    public void setRed(Integer[] red) {
        this.red = red;
    }

    public Integer[] getBlue() {
        return blue;
    }

    public void setBlue(Integer[] blue) {
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
        sb.append(id + ":");

        for (int i = 0; i < 5; i++) {
            sb.append(red[i] + ",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(";");
        for (int i = 0; i < 2; i++) {
            sb.append(blue[i] + ",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
}
