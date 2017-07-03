package me.junbin.commons.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeEnv;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.gson.reflect.TypeToken;
import me.junbin.commons.protostuff.strategy.SimpleWrapStrategy;
import me.junbin.commons.protostuff.strategy.WrapStrategy;
import me.junbin.commons.util.Args;
import me.junbin.commons.wrapper.ObjectWrapper;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@gmail.com">发送邮件</a>
 * @createDate : 2017/1/26 21:40
 * @description : 通过 {@link System#getProperties()} 之后设置系统属性 {@link RuntimeEnv#ALWAYS_USE_SUN_REFLECTION_FACTORY}
 * 可以限制构造方法都只通过 {@link sun.reflect.ReflectionFactory} 来获取；具体可以参考 {@link RuntimeEnv}
 * 另外通过设置系统属性 {@link RuntimeEnv#USE_SUN_MISC_UNSAFE} 可以限制仅仅通过 {@link sun.misc.Unsafe} 来处理对象的字段（该方式在 JRE 下运行默认启动）；
 * Protostuff 无法数组、抽象类、接口的 Schema，因此也无法实现序列化；虽然 Protostuff 可以获取枚举类的 Schema，但是
 * 在（反）序列化时被处理成 null，因此也无法直接（反）序列化。
 * ★★ 强制要求对 {@link String} 进行包装；
 * ★★ 强制要求不对 {@link ObjectWrapper} 执行包装操作。
 */
public abstract class Protostuffs {

    static {
        System.getProperties().setProperty("protostuff.runtime.always_use_sun_reflection_factory", "true");
    }

    private static final int cacheSize = 256;
    private static final int bufferSize = 4096;
    private static WrapStrategy strategy = new SimpleWrapStrategy();
    private static final Objenesis objenesis = new ObjenesisStd(true);
    /**
     * 缓存 {@link Class} 与其对应的 {@link Schema}
     */
    private static final ConcurrentMap<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>(cacheSize);
    /**
     * 缓存 {@link Class} 与其对应的 {@link ObjectInstantiator}
     */
    private static final ConcurrentMap<Class<?>, ObjectInstantiator<?>> instantiatorCache = new ConcurrentHashMap<>(cacheSize);
    /**
     * 缓存 {@link Class} 及其是否需要包装
     */
    private static final ConcurrentMap<Class<?>, Boolean> needWrappedCache = new ConcurrentHashMap<>(cacheSize);

    static {
        //schemaCache.put(String.class, RuntimeSchema.getSchema(String.class));
        schemaCache.put(ObjectWrapper.class, RuntimeSchema.getSchema(ObjectWrapper.class));
        //instantiatorCache.put(String.class, objenesis.getInstantiatorOf(String.class));
        instantiatorCache.put(ObjectWrapper.class, objenesis.getInstantiatorOf(ObjectWrapper.class));
        needWrappedCache.put(String.class, Boolean.TRUE);
        needWrappedCache.put(ObjectWrapper.class, Boolean.FALSE);
        needWrappedCache.put(Collection.class, Boolean.TRUE);
        needWrappedCache.put(List.class, Boolean.TRUE);
        needWrappedCache.put(ArrayList.class, Boolean.TRUE);
        needWrappedCache.put(LinkedList.class, Boolean.TRUE);
        needWrappedCache.put(Set.class, Boolean.TRUE);
        needWrappedCache.put(HashSet.class, Boolean.TRUE);
        needWrappedCache.put(Map.class, Boolean.TRUE);
        needWrappedCache.put(HashMap.class, Boolean.TRUE);
        needWrappedCache.put(ConcurrentMap.class, Boolean.TRUE);
        needWrappedCache.put(int[].class, Boolean.TRUE);
    }

    /**
     * 从缓存中获取指定类的 {@link Schema}，如果缓存中不存在该类对应的 {@link Schema}，那么直接
     * 通过运行环境获取该类的 {@link Schema} 并存入缓存中。
     * 切记数组、抽象类和接口是没有 {@link Schema} 的，如果传入这三种类型将直接抛出 {@link RuntimeException}
     *
     * @param clazz 需要获取 {@link Schema} 的类
     * @param <T>   泛型
     * @return 指定类对应的 {@link Schema}
     */
    public static <T> Schema<T> getSchema(final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                // green(String.format("缓存 {%s} 的 Schema.", clazz.getName()));
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }

    /**
     * 从缓存中获取指定类的 {@link ObjectInstantiator}，如果缓存中不存在该类对应的 {@link ObjectInstantiator}，
     * 那么直接新建该类的 {@link ObjectInstantiator} 并存入缓存中。
     *
     * @param clazz 需要获取 {@link ObjectInstantiator} 的类
     * @param <T>   泛型
     * @return 指定类对应的 {@link ObjectInstantiator}
     */
    public static <T> ObjectInstantiator<T> getInstantiator(final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        ObjectInstantiator<T> instantiator = (ObjectInstantiator<T>) instantiatorCache.get(clazz);
        if (instantiator == null) {
            instantiator = objenesis.getInstantiatorOf(clazz);
            if (instantiator != null) {
                instantiatorCache.put(clazz, instantiator);
            }
        }
        return instantiator;
    }

    /**
     * 使用 protostuff 将对象序列化为字节数组
     *
     * @param obj 任意对象
     * @param <T> 泛型
     * @return 指定对象对应的 protostuff 序列化数组
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(final T obj) {
        T notNullObj = Args.notNull(obj);
        Class clazz = notNullObj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(bufferSize);
        try {
            if (needWrapped(clazz)) {
                ObjectWrapper wrapper = new ObjectWrapper(notNullObj);
                Schema<ObjectWrapper> schema = getSchema(ObjectWrapper.class);
                return ProtostuffIOUtil.toByteArray(wrapper, schema, buffer);
            } else {
                Schema schema = getSchema(clazz);
                return ProtostuffIOUtil.toByteArray(notNullObj, schema, buffer);
            }
        } finally {
            buffer.clear();
        }
    }

    /**
     * 使用 protostuff 将字节数组反序列为指定类型的对象
     *
     * @param data    字节数组
     * @param typeOfT 对象的类型
     * @param <T>     泛型
     * @return 返回字节数组对应的指定类型的实例，如果发生了类型不兼容等问题将抛出 {@link RuntimeException}
     */
    public static <T> T deserialize(byte[] data, Class<T> typeOfT) {
        byte[] notNullData = Args.notNull(data);
        Args.notNull(typeOfT);
        if (needWrapped(typeOfT)) {
            Schema<ObjectWrapper> schema = getSchema(ObjectWrapper.class);
            ObjectWrapper wrapper = getInstantiator(ObjectWrapper.class).newInstance();
            ProtostuffIOUtil.mergeFrom(notNullData, wrapper, schema);
            //noinspection unchecked
            return (T) wrapper.getSource();
        } else {
            Schema<T> schema = getSchema(typeOfT);
            T object = getInstantiator(typeOfT).newInstance();
            ProtostuffIOUtil.mergeFrom(notNullData, object, schema);
            return object;
        }
    }

    /**
     * 使用 protostuff 将字节数组反序列为指定类型的对象
     *
     * @param data      字节数组
     * @param typeToken 对象的泛型说明
     * @param <T>       泛型
     * @return 返回字节数组对应的指定类型的实例，如果发生了类型不兼容等问题将抛出 {@link RuntimeException}
     */
    public static <T> T deserialize(byte[] data, TypeToken<T> typeToken) {
        @SuppressWarnings("unchecked")
        Class<T> rawType = (Class<T>) Args.notNull(typeToken).getRawType();
        return deserialize(data, rawType);
    }

    /**
     * 决定指定类型是否需要使用包装器
     *
     * @param clazz 任意类
     * @return 是否使用包装器由 {@link #strategy} 包装策略决定
     */
    private static boolean needWrapped(Class<?> clazz) {
        Boolean flag = needWrappedCache.get(clazz);
        if (flag == null) {
            flag = strategy.shouldUsingWrapper(clazz);
            needWrappedCache.put(clazz, flag);
        }
        return flag;
    }

    /**
     * 设置新的包装策略，此时会清空类包装缓存
     *
     * @param wrapStrategy 包装策略
     */
    public static void setStrategy(WrapStrategy wrapStrategy) {
        strategy = Args.notNull(wrapStrategy);
        needWrappedCache.clear();
    }

    public static void clearSchemaCache() {
        schemaCache.clear();
        //schemaCache.put(String.class, RuntimeSchema.getSchema(String.class));
        schemaCache.put(ObjectWrapper.class, RuntimeSchema.getSchema(ObjectWrapper.class));
    }

    public static void clearInstantiatorCache() {
        instantiatorCache.clear();
        //instantiatorCache.put(String.class, objenesis.getInstantiatorOf(String.class));
        instantiatorCache.put(ObjectWrapper.class, objenesis.getInstantiatorOf(ObjectWrapper.class));
    }

    public static void clearNeedWrappedCache() {
        needWrappedCache.clear();
        needWrappedCache.put(String.class, Boolean.TRUE);
        needWrappedCache.put(ObjectWrapper.class, Boolean.FALSE);
    }

}
