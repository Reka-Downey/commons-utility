package me.junbin.commons.wrapper;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/12/31 11:04
 * @description : 由于数组不存在父类或者父接口，因此无法像 {@link java.util.List} 或者 {@link java.util.Map}
 * 一样被直接包装成 {@link ListWrapper} 或者 {@link MapWrapper}。因此这里使用了 {@link Object} 来代表数组对象。
 * 支持一维数组和多维数组的包装。
 */
public class ArrayWrapper implements Wrapper {

    private Object source;

    public ArrayWrapper() {
    }

    public ArrayWrapper(Object source) {
        this();
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
