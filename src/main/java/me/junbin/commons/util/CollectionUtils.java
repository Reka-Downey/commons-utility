package me.junbin.commons.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static <T> T[] toArray(List<T> list, Class<T> typeOfT) {
        return toArray(list, typeOfT, 0);
    }

    public static <T> T[] toArray(List<T> list, Class<T> typeOfT, int startIncludeIndex) {
        Args.notNull(list);
        return toArray(list, typeOfT, startIncludeIndex, list.size());
    }

    public static <T> T[] toArray(List<T> list, Class<T> typeOfT, int startIncludeIndex, int endExcludeIndex) {
        Args.notNull(typeOfT);
        if (startIncludeIndex > endExcludeIndex) {
            throw new IllegalArgumentException(String.format("start index[%d] must smaller or equals than end index[%d]", startIncludeIndex, endExcludeIndex));
        }
        if (CollectionUtils.isEmpty(list) && startIncludeIndex > 0) {
            throw new IllegalArgumentException(String.format("list is empty but start index[%d] is bigger than 0", startIncludeIndex));
        }
        int size = list.size();
        if (endExcludeIndex > size) {
            throw new IllegalArgumentException(String.format("endExcludeIndex[%d] is bigger than list size[%d]", endExcludeIndex, size));
        }
        int arrayLength = endExcludeIndex - startIncludeIndex;
        //noinspection unchecked
        T[] result = (T[]) Array.newInstance(typeOfT, arrayLength);
        int index = 0;
        for (int i = startIncludeIndex; i < endExcludeIndex; i++) {
            result[index++] = list.get(i);
        }
        return result;
    }
    // 更多关于数组的操作可以直接使用 java.lang.reflect.Array

}
