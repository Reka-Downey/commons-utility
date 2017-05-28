package me.junbin.commons.converter.custom.gson;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:59
 * @description :
 */
public interface GsonEnum<E extends Enum<E>> {

    String serialize();

    E deserialize(String json);

}
