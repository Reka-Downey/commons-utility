package me.junbin.commons.util;

import java.util.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/25 21:39
 * @description :
 */
public abstract class CollectionUtils {

    public static <E> boolean isEmpty(final Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <E> boolean notEmpty(final Collection<E> collection) {
        return !isEmpty(collection);
    }

    public static <K, V> boolean isEmpty(final Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean notEmpty(final Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 指定 {@link Class} 对象是否是 {@link Collection} 或者 {@link Collection} 的子接口
     * 或者实现类（记为“条件”）
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 满足“条件”下返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isCollection(final Class<?> clazz) {
        Class<?> notnullClass = Args.notNull(clazz);
        return Collection.class.isAssignableFrom(notnullClass);
    }

    /**
     * 指定 {@link Class} 对象是否是 {@link List} 或者 {@link List} 的子接口或者实现类（记为“条件”）
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 满足“条件”下返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isList(final Class<?> clazz) {
        Class<?> notnullClass = Args.notNull(clazz);
        return List.class.isAssignableFrom(notnullClass);
    }

    /**
     * 指定 {@link Class} 对象是否是 {@link Set} 或者 {@link Set} 的子接口或者实现类（记为“条件”）
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 满足“条件”下返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isSet(final Class<?> clazz) {
        Class<?> notnullClass = Args.notNull(clazz);
        return Set.class.isAssignableFrom(notnullClass);
    }

    /**
     * 指定 {@link Class} 对象是否是 {@link Map} 或者 {@link Map} 的子接口或者实现类（记为“条件”）
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 满足“条件”下返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isMap(final Class<?> clazz) {
        Class<?> notnullClass = Args.notNull(clazz);
        return Map.class.isAssignableFrom(notnullClass);
    }

    /**
     * 指定 {@link Class} 对象是否是 数组
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 如果 {@link Class} 是数组，那么返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isArray(final Class<?> clazz) {
        Class<?> notnullClass = Args.notNull(clazz);
        //return Array.class.isAssignableFrom(notnullClass);
        return notnullClass.isArray();
    }

    /**
     * 指定 {@link Class} 对象是否是 数组、{@link Map} 或者 {@link Collection} 或者他们的子接口或者
     * 实现类
     *
     * @param clazz 受检查的 {@link Class} 对象
     * @return 如果指定 {@link Class} 对象不是容器类，那么返回 {@code false}，否则返回 {@code true}
     */
    public static boolean isContainer(final Class<?> clazz) {
        return isCollection(clazz) ||
                isMap(clazz) ||
                isArray(clazz);
    }

    public static Class<?> getComponentType(final Object arr) {
        Class<?> clazz = Args.notNull(arr).getClass();
        return isArray(clazz) ? clazz.getComponentType() : null;
    }

    public static Class<?> getComponentType(final Class<?> arr) {
        Class<?> notNullClass = Args.notNull(arr);
        return isArray(notNullClass) ? notNullClass.getComponentType() : null;
    }

    // 更多关于数组的操作可以直接使用 java.lang.reflect.Array

}
