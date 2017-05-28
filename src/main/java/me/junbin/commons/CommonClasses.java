package me.junbin.commons;

import java.util.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 12:42
 * @description :
 */
public abstract class CommonClasses {

    private static final Class<?>[] primitive = {byte.class, char.class, short.class, int.class, long.class, float.class, double.class, boolean.class};
    public static final List<Class<?>> PRIMITIVE = Collections.unmodifiableList(Arrays.asList(primitive));

    private static final Class<?>[] primitiveVoid = {byte.class, char.class, short.class, int.class, long.class, float.class, double.class, boolean.class, void.class};
    public static final List<Class<?>> PRIMITIVE_VOID = Collections.unmodifiableList(Arrays.asList(primitiveVoid));

    private static final Class<?>[] wrapper = {Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class};
    public static final List<Class<?>> WRAPPER = Collections.unmodifiableList(Arrays.asList(wrapper));

    private static final Class<?>[] wrapperVoid = {Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Void.class};
    public static final List<Class<?>> WRAPPER_VOID = Collections.unmodifiableList(Arrays.asList(wrapperVoid));

    public static final Map<String, Class<?>> PRIMITIVE_MAP;

    static {
        Map<String, Class<?>> primitiveNameMap = new HashMap<>();
        for (Class<?> clazz : primitive) {
            primitiveNameMap.put(clazz.getName(), clazz);
        }
        PRIMITIVE_MAP = Collections.unmodifiableMap(primitiveNameMap);
    }

    public static final Map<String, Class<?>> WRAPPER_MAP;

    static {
        Map<String, Class<?>> wrapperNameMap = new HashMap<>();
        for (Class<?> clazz : wrapper) {
            wrapperNameMap.put(clazz.getName(), clazz);
        }
        WRAPPER_MAP = Collections.unmodifiableMap(wrapperNameMap);
    }

}
