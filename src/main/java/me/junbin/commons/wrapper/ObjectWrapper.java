package me.junbin.commons.wrapper;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@gmail.com">发送邮件</a>
 * @createDate : 2017/6/26 21:54
 * @description : 针对接口、抽象类、数组、枚举等 {@code protostuff} 无法直接（反）序列化的类的包装
 */
public class ObjectWrapper implements Wrapper {

    private Object source;

    public ObjectWrapper(Object source) {
        this.source = source;
    }

    @Override
    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

}
