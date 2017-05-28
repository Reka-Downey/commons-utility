package me.junbin.commons.util;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 21:54
 * @description :
 */
public abstract class Reflections {

    /**
     * <pre>
     *      {@link Thread#getStackTrace()} 方法可以获取当前的帧栈，其中栈顶固定为：{@code java.lang.Thread.getStackTrace}；
     *      栈[1] 为当前类的当前方法，在这里为：{@code package.Reflections.getCallerClass}；
     *      栈[2] 为当前类的上级调用者信息
     *      栈[3] 为当前类的上上级调用者信息，因此栈[3]是栈[2]的调用者
     *
     *      StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
     *      StackTraceElement stack = traceElements[0];
     *      StackTraceElement current = traceElements[1];
     *      StackTraceElement currentCaller = traceElements[2];
     *      StackTraceElement callerCaller = traceElements[3];
     * </pre>
     */
    public static Class<?> getCallerClass() {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        if (traceElements == null || traceElements.length < 4) {
            throw new RuntimeException("No Caller found!");
        }
        try {
            return Class.forName(traceElements[3].getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCallerClassName() {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        if (traceElements == null || traceElements.length < 4) {
            throw new RuntimeException("No Caller found!");
        }
        return traceElements[3].getClassName();
    }

    /**
     * <pre>
     *     level        类
     *       0          调用当前方法的类
     *       1          调用当前方法的类的上级调用者
     *       2          调用当前方法的类的上上级调用者
     * </pre>
     *
     * @param level 调用者层级，0表示自身
     * @return 调用者类
     */
    public static Class<?> getCallerClass(final int level) {
        if (level < 0) {
            throw new IllegalArgumentException("Caller Level must not be negative!");
        }
        int stackLevel = level + 2;
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        if (traceElements == null || traceElements.length < stackLevel + 1) {
            throw new RuntimeException("No Caller found!");
        }
        try {
            return Class.forName(traceElements[stackLevel].getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCallerClassName(final int level) {
        if (level < 0) {
            throw new IllegalArgumentException("Caller Level must not be negative!");
        }
        int stackLevel = level + 2;
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        if (traceElements == null || traceElements.length < stackLevel + 1) {
            throw new RuntimeException("No Caller found!");
        }
        return traceElements[stackLevel].getClassName();
    }

    public static String getCallerMethod() {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        if (traceElements == null || traceElements.length < 4) {
            throw new RuntimeException("No Caller found!");
        }
        return traceElements[3].getMethodName();
    }

}
