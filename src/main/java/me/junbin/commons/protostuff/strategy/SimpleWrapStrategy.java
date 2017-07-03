package me.junbin.commons.protostuff.strategy;

import java.lang.reflect.Modifier;

import static me.junbin.commons.util.CollectionUtils.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@gmail.com">发送邮件</a>
 * @createDate : 2017/6/27 23:36
 * @description : 简单包装策略：如果某个类是 {@link java.util.List}、{@link java.util.Set}、
 * {@link java.util.Map}、{@link Enum}、数组、抽象类则执行包装
 */
public class SimpleWrapStrategy implements WrapStrategy {

    @Override
    public Boolean shouldUsingWrapper(Class<?> notNullClass) {
        return isCollection(notNullClass) || isMap(notNullClass)
                || isArray(notNullClass) || notNullClass.isEnum()
                || Modifier.isAbstract(notNullClass.getModifiers());
    }

}
