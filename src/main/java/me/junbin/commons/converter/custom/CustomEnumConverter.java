package me.junbin.commons.converter.custom;

import com.google.gson.*;
import me.junbin.commons.converter.custom.gson.GsonTypeAdapter;
import me.junbin.commons.converter.custom.mybatis.MyBatisEnumTypeHandler;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 20:00
 * @description : 继承这个类之后，可以使用 {@link org.apache.ibatis.type.MappedJdbcTypes}
 * 和 {@link org.apache.ibatis.type.MappedTypes} 或者 {@link Converter#autoApply()} 进行
 * 自动匹配调用
 */
public class CustomEnumConverter<E extends Enum<E> & CustomEnum<E>>
        extends MyBatisEnumTypeHandler<E>
        implements GsonTypeAdapter<E>, AttributeConverter<E, String> {

    private final E e;

    /**
     * 此方法只能够由直接继承 {@link CustomEnumConverter} 的子类构造方法来调用。
     * 通过反射来获取子类中声明的枚举泛型。
     * 不建议使用此构造方法，子类应当在默认构造方法中使用 {@code super(CustomEnum)} 来执行实例构造
     */
    public CustomEnumConverter() {
        Class<? extends CustomEnumConverter> thisClazz = this.getClass();
        if (thisClazz == CustomEnumConverter.class) {
            throw new IllegalArgumentException("This constructor must only invokes by the subclass of CustomEnumConverter");
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

    public CustomEnumConverter(E e) {
        super(e);
        this.e = e;
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        return null == src ? null : new JsonPrimitive(src.serialize());
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return null == json ? null : e.deserialize(json.getAsString());
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return null == attribute ? null : attribute.convertToDatabaseColumn();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return null == dbData ? null : e.convertToEntityAttribute(dbData);
    }

}
