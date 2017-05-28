package me.junbin.commons.converter.api.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 19:44
 * @description :
 */
public class MyBatisEnumTypeHandler<E extends Enum<E> & MyBatisEnum<E>> extends BaseTypeHandler<E> {

    private final E e;

    /**
     * 此方法只能够由直接继承 {@link MyBatisEnumTypeHandler} 的子类构造方法来调用。
     * 通过反射来获取子类中声明的枚举泛型。
     * 不建议使用此构造方法，子类应当在默认构造方法中使用 {@code super(MyBatisEnum)} 来执行实例构造
     */
    protected MyBatisEnumTypeHandler() {
        Class<? extends MyBatisEnumTypeHandler> thisClazz = this.getClass();
        if (thisClazz == MyBatisEnumTypeHandler.class) {
            throw new IllegalArgumentException("This constructor must only invokes by the subclass of MyBatisEnumTypeHandler");
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

    public MyBatisEnumTypeHandler(E e) {
        this.e = e;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.convertToDatabaseColumn());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return transfer(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return transfer(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return transfer(cs.getString(columnIndex));
    }

    private E transfer(String dbData) {
        return dbData == null ? null : e.convertToEntityAttribute(dbData);
    }

}
