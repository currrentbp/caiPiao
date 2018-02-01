package com.bp.common.entity;

import java.util.List;

/**
 * 关于该期大乐透与前N期数据的重合度
 *
 * @author current_bp
 * @createTime 20180131
 */
public class ProblemDate {
    //todo not work:没有想好结构
    private Integer daletouId;
    //重复的概率
    private Float redProblem;
    private Float blueProblem;
    //重复的数据
    private List<Integer> redRepeats;
    private List<Integer> blueRepeats;

    public Integer getDaletouId() {
        return daletouId;
    }

    public void setDaletouId(Integer daletouId) {
        this.daletouId = daletouId;
    }

    public Float getRedProblem() {
        return redProblem;
    }

    public void setRedProblem(Float redProblem) {
        this.redProblem = redProblem;
    }

    public Float getBlueProblem() {
        return blueProblem;
    }

    public void setBlueProblem(Float blueProblem) {
        this.blueProblem = blueProblem;
    }

    public List<Integer> getRedRepeats() {
        return redRepeats;
    }

    public void setRedRepeats(List<Integer> redRepeats) {
        this.redRepeats = redRepeats;
    }

    public List<Integer> getBlueRepeats() {
        return blueRepeats;
    }

    public void setBlueRepeats(List<Integer> blueRepeats) {
        this.blueRepeats = blueRepeats;
    }
}
