package me.junbin.commons.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/27 19:38
 * @description :
 */
public final class UnsafeHolder {

    private UnsafeHolder() {
    }

    private static final Unsafe UNSAFE;

    static {
        final PrivilegedExceptionAction<Unsafe> action = () -> {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        };
        try {
            UNSAFE = AccessController.doPrivileged(action);
        } catch (PrivilegedActionException e) {
            throw new RuntimeException("Can not get the unsafe instance", e);
        }
    }

    public static Unsafe get() {
        return UNSAFE;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz) throws InstantiationException {
        return (T) get().allocateInstance(clazz);
    }

}
