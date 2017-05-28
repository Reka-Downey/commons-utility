package me.junbin.commons.converter.custom.mybatis;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 19:43
 * @description :
 */
public interface MyBatisEnum<E extends Enum<E>> {

    String convertToDatabaseColumn();

    E convertToEntityAttribute(String dbData);

}
