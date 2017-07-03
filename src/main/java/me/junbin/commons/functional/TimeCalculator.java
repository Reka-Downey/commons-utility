package me.junbin.commons.functional;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/6/28 22:00
 * @description :
 */
public class TimeCalculator {

    public static long nanoElapse(Action action) throws Exception {
        long start = System.nanoTime();
        action.execute();
        long end = System.nanoTime();
        return end - start;
    }

    public static long nanoElapse(Action action, ErrorHandler errorHandler) {
        long start = System.nanoTime();
        try {
            action.execute();
        } catch (Exception e) {
            errorHandler.handle(e);
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long nanoElapseQuiet(Action action) {
        long start = System.nanoTime();
        try {
            action.execute();
        } catch (Exception ignored) {
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long millElapse(Action action) throws Exception {
        long start = System.currentTimeMillis();
        action.execute();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long millElapse(Action action, ErrorHandler errorHandler) {
        long start = System.currentTimeMillis();
        try {
            action.execute();
        } catch (Exception e) {
            errorHandler.handle(e);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long millElapseQuiet(Action action) {
        long start = System.currentTimeMillis();
        try {
            action.execute();
        } catch (Exception ignored) {
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

}
