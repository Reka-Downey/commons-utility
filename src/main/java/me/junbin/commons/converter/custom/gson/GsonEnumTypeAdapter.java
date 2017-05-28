package me.junbin.commons.converter.custom.gson;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-17 21:15
 * @description :
 */
public class GsonEnumTypeAdapter<E extends Enum<E> & GsonEnum<E>> implements GsonTypeAdapter<E> {

    private final E e;

    /**
     * 此方法只能够由直接继承 {@link GsonEnumTypeAdapter} 的子类构造方法来调用。
     * 通过反射来获取子类中声明的枚举泛型。
     * 不建议使用此构造方法，子类应当在默认构造方法中使用 {@code super(GsonEnum)} 来执行实例构造
     */
    protected GsonEnumTypeAdapter() {
        Class<? extends GsonEnumTypeAdapter> thisClazz = this.getClass();

        if (thisClazz == GsonEnumTypeAdapter.class) {
            throw new IllegalArgumentException("This constructor must only invokes by the subclass of GsonEnumTypeAdapter");
        }

        Type type = thisClazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("super class must be ParameterizedType");
        }
        ParameterizedType pType = (ParameterizedType) type;
        Type[] types = pType.getActualTypeArguments();
        if (types.length != 1) {
            throw new IllegalArgumentException("Generic parameter must only One!");
        }
        Type enumType = types[0];
        if (!(enumType instanceof Class)) {
            throw new IllegalArgumentException("Generic parameter must be Class");
        }
        Class<?> clazz = (Class<?>) enumType;
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("Generic parameter must be Enum");
        }
        Object[] enums = clazz.getEnumConstants();
        if (enums.length < 1) {
            throw new IllegalArgumentException("Enum must contains item");
        }
        Object enum0 = enums[0];
        //noinspection unchecked
        this.e = (E) enum0;
    }

    public GsonEnumTypeAdapter(E e) {
        this.e = e;
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        return null == src ? null : new JsonPrimitive(src.serialize());
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null == json ? null : e.deserialize(json.getAsString());
    }

}

