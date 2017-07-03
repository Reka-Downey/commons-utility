package me.junbin.commons.wrapper;

import java.io.Serializable;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/12/31 9:03
 * @description :
 */
@FunctionalInterface
public interface Wrapper<T> extends Serializable {

    T getSource();

}
