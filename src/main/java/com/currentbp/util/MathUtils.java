package com.currentbp.util;

import com.currentbp.util.all.Assert;
import org.junit.Test;

/**
 * @author current_bp
 * @createTime 20180424
 */
public class MathUtils {

    public static long c(int m, int n) {
        Assert.isTrue(m <= m, "数学表达式不正确，m<=m");
        long result = factorial(m, n) / factorial(m);

        return result;
    }

    /**
     * 阶乘:A!
     *
     * @param n 数值
     * @return 结果
     */
    public static long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    /**
     * 组合:Amn
     *
     * @param m 上数
     * @param n 下数
     * @return 结果
     */
    public static long factorial(int m, int n) {
        long result = 1;
        for (int i = n - m + 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    @Test
    public void f1() {
        long factorial = MathUtils.factorial(5);
        System.out.println(factorial);
    }

    @Test
    public void f() {
        long factorial = MathUtils.factorial(2, 4);
        System.out.println(factorial);
    }


}
