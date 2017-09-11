package com.bp.shuangseqiu;

import java.util.Arrays;

/**
 * 双色球实体
 *
 * @author current_bp
 * @createTime 20170909
 */
public class ShuangseqiuEntity {

    private String id;
    private Integer[] red = new Integer[6];
    private Integer blue = 0;

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
        return "ShuangseqiuEntity{" +
                "id='" + id + '\'' +
                ", red=" + Arrays.toString(red) +
                ", blue=" + blue +
                '}';
    }
}
