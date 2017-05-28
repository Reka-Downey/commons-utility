package me.junbin.commons.converter.api;


import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.api.gson.GsonTypeAdapter;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 15:47
 * @description :
 */
public class JpaGsonEnumConverter<E extends Enum<E> & JpaGsonEnum<E>>
        extends GsonTypeAdapter<E> implements AttributeConverter<E, String> {

    private final E e;

    /**
     * 此方法只能够由直接继承 {@link JpaGsonEnumConverter} 的子类构造方法来调用。
     * 通过反射来获取子类中声明的枚举泛型。
     * 不建议使用此构造方法，子类应当在默认构造方法中使用 {@code super(JpaGsonEnum)} 来执行实例构造
     */
    protected JpaGsonEnumConverter() {
        Class<? extends JpaGsonEnumConverter> thisClazz = this.getClass();
        if (thisClazz == JpaGsonEnumConverter.class) {
            throw new IllegalArgumentException("This constructor must only invokes by the subclass of JpaGsonEnumConverter");
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

    public JpaGsonEnumConverter(E e) {
        this.e = e;
    }

    @Override
    public void write(JsonWriter out, E value) throws IOException {
        out.value(value == null ? null : value.freeze());
    }

    @Override
    public E read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if (jsonToken == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return e.unfreeze(in.nextString());
        }
        return null;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.freeze();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return e.unfreeze(dbData);
    }

}
