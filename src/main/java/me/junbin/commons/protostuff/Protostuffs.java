package me.junbin.commons.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeEnv;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import me.junbin.commons.util.Args;
import me.junbin.commons.wrapper.*;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static me.junbin.commons.ansi.ColorfulPrinter.green;
import static me.junbin.commons.ansi.ColorfulPrinter.red;
import static me.junbin.commons.util.CollectionUtils.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/26 21:40
 * @description : 通过 {@link System#getProperties()} 之后设置系统属性 {@link RuntimeEnv#ALWAYS_USE_SUN_REFLECTION_FACTORY}
 * 可以限制构造方法都只通过 {@link sun.reflect.ReflectionFactory} 来获取；具体可以参考 {@link RuntimeEnv}
 * 另外通过设置系统属性 {@link RuntimeEnv#USE_SUN_MISC_UNSAFE} 可以限制仅仅通过 {@link sun.misc.Unsafe} 来处理对象的字段（该方式在 JRE 下运行默认启动）；
 */
public final class Protostuffs {


    static {
        System.getProperties().setProperty("protostuff.runtime.always_use_sun_reflection_factory", "true");
    }

    /**
     * 缓存 {@link Class} 与其对应的 {@link Schema}
     */
    private static final ConcurrentMap<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    /**
     * 用来反射生成对象
     */
    private static final Objenesis objenesis = new ObjenesisStd(true);


    static {
        schemaCache.put(String.class, RuntimeSchema.getSchema(String.class));
        /*
         * 由于容器类经常会被用到，因此先加载常用的容器类与其对应的包装类的 {@link Schema} 到缓存中；
         * 这里之所以需要使用容器的包装类，是因为 Protostuff 并不支持直接（反）序列化容器对象
         */
        schemaCache.put(ListWrapper.class, RuntimeSchema.getSchema(ListWrapper.class));
        schemaCache.put(SetWrapper.class, RuntimeSchema.getSchema(SetWrapper.class));
        schemaCache.put(MapWrapper.class, RuntimeSchema.getSchema(MapWrapper.class));
    }

    private Protostuffs() {
    }

    /**
     * 从缓存中获取指定类的 {@link Schema}，如果缓存中不存在该类对应的 {@link Schema}，那么直接
     * 通过运行环境获取该类的 {@link Schema} 并存入缓存中。
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
                green(String.format("缓存 {%s} 的 Schema.", clazz.getName()));
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }

    /**
     * 使用 protostuff 将对象序列化为字节数组
     *
     * @param obj 任意对象
     * @param <T> 泛型
     * @return 指定对象对应的 protostuff 序列化数组
     */
    public static <T> byte[] serialize(final T obj) {
        T notNullObj = Args.notNull(obj);
        /* 判断并处理容器类元素 */
        Wrapper wrapper = toWrapper(obj);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        if (wrapper != null) { // obj 是容器对象
            @SuppressWarnings("unchecked")
            Class<Wrapper> wrapperClass = (Class<Wrapper>) wrapper.getClass();
            try {
                Schema<Wrapper> wrapperSchema = getSchema(wrapperClass);
                return ProtobufIOUtil.toByteArray(wrapper, wrapperSchema, buffer);
            } finally {
                buffer.clear();
            }
        } else { // obj 不是容器对象
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) obj.getClass();
            try {
                Schema<T> schema = getSchema(clazz);
                return ProtobufIOUtil.toByteArray(notNullObj, schema, buffer);
            } finally {
                buffer.clear();
            }
        }
    }

    /**
     * 使用 protostuff 将字节数组反序列为指定类型的对象
     *
     * @param data    字节数组
     * @param typeOfT 对象的类型
     * @param <T>     泛型
     * @return 返回字节数组对应的指定类型的实例，如果发生了类型不兼容等问题将返回 {@code null}
     */
    public static <T> T deserialize(final byte[] data, final Class<T> typeOfT) {
        byte[] notNullData = Args.notNull(data);
        Class<T> notNullClass = Args.notNull(typeOfT);
        @SuppressWarnings("unchecked")
        Class<Wrapper> wrapperClass = (Class<Wrapper>) toWrapperClass(typeOfT);
        if (wrapperClass != null) { // 该序列化字节数组是容器对象
            Wrapper wrapper;
            try {
                wrapper = objenesis.newInstance(wrapperClass);
                Schema<Wrapper> schema = getSchema(wrapperClass);
                ProtobufIOUtil.mergeFrom(data, wrapper, schema);
            } catch (Exception e) {
                red("异常 -->" + e);
                throw new RuntimeException(e);
            }
            //noinspection unchecked
            return (T) wrapper.getSource();
        } else { // 该序列化字节数组是普通对象
            T obj;
            try {
                obj = objenesis.newInstance(notNullClass);
                Schema<T> schema = getSchema(notNullClass);
                ProtobufIOUtil.mergeFrom(notNullData, obj, schema);
            } catch (Exception e) {
                red("异常 -->" + e);
                throw new RuntimeException(e);
            }
            return obj;
        }
    }


    /**
     * 如果指定类型是常用容器类，那么返回该类对应的容器包装类
     *
     * @param typeOfTarget 任意类
     * @return 如果指定类型是常用容器类，那么返回该类对应的容器包装类，否则返回 {@code null}
     */
    private static Class<? extends Wrapper> toWrapperClass(Class<?> typeOfTarget) {
        if (isMap(typeOfTarget)) {
            return MapWrapper.class;
        }
        if (isList(typeOfTarget)) {
            return ListWrapper.class;
        }
        if (isSet(typeOfTarget)) {
            return SetWrapper.class;
        }
        if (isArray(typeOfTarget)) {
            return ArrayWrapper.class;
        }
        return null;
    }

    /**
     * 如果对象是常用的容器类，那么将该类封装到对应的包装类中并返回该包装类
     *
     * @param source 任意对象
     * @return 如果对象是容器对象，那么返回该对象的容器包装类，否则返回 {@code null}
     */
    private static Wrapper toWrapper(Object source) {
        Class<?> clazz = source.getClass();
        if (isMap(clazz)) {
            return new MapWrapper((Map) source);
        }
        if (isList(clazz)) {
            return new ListWrapper((List) source);
        }
        if (isSet(clazz)) {
            return new SetWrapper((Set) source);
        }
        if (isArray(clazz)) {
            return new ArrayWrapper(source);
        }
        return null;
    }

}
