package me.junbin.commons.protostuff.strategy;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@gmail.com">发送邮件</a>
 * @createDate : 2017/6/27 23:35
 * @description :
 */
@FunctionalInterface
public interface WrapStrategy {

    Boolean shouldUsingWrapper(Class<?> notNullClass);

}
