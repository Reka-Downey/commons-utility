package me.junbin.commons.wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/12/31 9:02
 * @description : 对纯粹的 {@link List} 进行包装，以便 Protostuff 可以对其进行序列化和反序列化
 */
public class ListWrapper implements Wrapper<List>, Serializable {

    private List source;

    public ListWrapper() {
    }

    public ListWrapper(List source) {
        this();
        this.source = source;
    }

    @Override
    public String toString() {
        return "ListWrapper{" +
                "source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListWrapper that = (ListWrapper) o;
        return Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }

    @Override
    public List getSource() {
        return source;
    }

    public void setSource(List source) {
        this.source = source;
    }

}
