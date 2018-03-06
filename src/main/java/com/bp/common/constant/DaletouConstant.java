package com.bp.common.constant;

/**
 * 大乐透的常量
 *
 * @author baopan
 * @createTime 20180305
 */
public class DaletouConstant {
    public static int MIN_DALETOU_ID = 7001;

    /**
     * 大乐透红球和篮球数量
     */
    public enum DaletouCount {
        REDS(5, "red's count"),
        BLUES(2, "blue's count");

        DaletouCount(int value, String name) {
            this.name = name;
            this.value = value;
        }

        private int value;
        private String name;

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 红球篮球的最大数字
     */
    public enum DaletouNumCount {
        REDS(35, "max red"),
        BLUES(12, "max blue");

        DaletouNumCount(int value, String name) {
            this.name = name;
            this.value = value;
        }

        private int value;
        private String name;

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
