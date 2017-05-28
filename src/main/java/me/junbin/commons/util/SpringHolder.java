package me.junbin.commons.util;

import me.junbin.commons.web.listener.AppListener;
import org.springframework.context.ApplicationContext;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 22:13
 * @description : 非 Web 环境下也可以使用，前提是 {@link #appCtx} 已经通过
 * {@link AppListener} 注册了
 */
public final class SpringHolder {

    private SpringHolder() {
    }

    private static ApplicationContext appCtx;

    public static void setAppCtx(ApplicationContext appCtx) {
        if (SpringHolder.appCtx == null) {
            Class<?> callerClass = Reflections.getCallerClass();
            if (AppListener.class.equals(callerClass)) {
                Args.notNull(appCtx);
                SpringHolder.appCtx = appCtx;
            }
        }
    }

    public static ApplicationContext getAppCtx() {
        return appCtx;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return appCtx.getBean(beanClass);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return appCtx.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return appCtx.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return appCtx.isSingleton(name);
    }

    public static boolean isPrototype(String name) {
        return appCtx.isPrototype(name);
    }

}
