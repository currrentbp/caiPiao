package com.bp.common.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * 历史中重复数据
 *
 * @author current_bp
 * @createTime 20180128
 */
public class HistoryDate {
    //当前的ID
    private Integer id;
    //包括当前的大乐透总的N期数据
    private List<Integer> reds = new ArrayList<Integer>();
    private List<Integer> blues = new ArrayList<Integer>();

    public HistoryDate() {
    }

    public void addReds(List<Integer> reds) {
        this.reds.addAll(reds);
    }

    public void addBlues(List<Integer> blues) {
        this.blues.addAll(blues);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "HistoryDate{" +
                "id=" + id +
                ", reds=" + reds +
                ", blues=" + blues +
                '}';
    }

    public List<Integer> getReds() {
        return reds;
    }

    public void setReds(List<Integer> reds) {
        this.reds = reds;
    }

    public List<Integer> getBlues() {
        return blues;
    }

    public void setBlues(List<Integer> blues) {
        this.blues = blues;
    }
}
