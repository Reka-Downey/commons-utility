package me.junbin.commons.converter.custom.jpa;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 19:48
 * @description :
 */
public class JpaEnumConverter<E extends Enum<E> & JpaEnum<E>> implements AttributeConverter<E, String> {

    private final E e;

    /**
     * 此方法只能够由直接继承 {@link JpaEnumConverter} 的子类构造方法来调用。
     * 通过反射来获取子类中声明的枚举泛型。
     * 不建议使用此构造方法，子类应当在默认构造方法中使用 {@code super(JpaEnum)} 来执行实例构造
     */
    protected JpaEnumConverter() {
        Class<? extends JpaEnumConverter> thisClazz = this.getClass();
        if (thisClazz == JpaEnumConverter.class) {
            throw new IllegalArgumentException("This constructor must only invokes by the subclass of JpaEnumConverter");
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

    public JpaEnumConverter(E e) {
        this.e = e;
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
