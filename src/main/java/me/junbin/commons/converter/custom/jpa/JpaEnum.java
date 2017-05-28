package me.junbin.commons.converter.custom.jpa;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 19:47
 * @description :
 */
public interface JpaEnum<E extends Enum<E>> {

    String convertToDatabaseColumn();

    E convertToEntityAttribute(String dbData);

}
