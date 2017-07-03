package me.junbin.commons.protostuff.strategy;

import java.io.Serializable;
import java.lang.reflect.Modifier;

import static me.junbin.commons.util.CollectionUtils.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@gmail.com">发送邮件</a>
 * @createDate : 2017/6/27 23:38
 * @description : 通用包装策略，在简单包装策略基础上，对继承了非 {@link Object} 最终父类
 * 或者实现了非 {@link Serializable} 接口的类进行包装
 */
public class CommonWrapStrategy implements WrapStrategy {

    @Override
    public Boolean shouldUsingWrapper(Class<?> notNullClass) {
        int modifiers = notNullClass.getModifiers();
        if (isCollection(notNullClass) || isMap(notNullClass)
                || isArray(notNullClass) || Modifier.isAbstract(modifiers)
                || notNullClass.isEnum()) { // 是容器、散列表、接口、抽象类、数组或者枚举
            return Boolean.TRUE;
        }
        Class<?> superclass = notNullClass.getSuperclass();
        if (superclass != Object.class) { // 存在非 Object 父类
            return Boolean.TRUE;
        }
        Class<?>[] interfaces = notNullClass.getInterfaces();
        int length = interfaces.length;
        if (length == 0 || (length == 1 && interfaces[0] == Serializable.class)) {
            return Boolean.FALSE;
        } else { // 实现了非 Serializable 接口
            return Boolean.TRUE;
        }
    }

}
