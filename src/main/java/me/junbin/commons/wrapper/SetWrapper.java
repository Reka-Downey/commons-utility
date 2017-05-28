package me.junbin.commons.wrapper;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/12/31 9:03
 * @description :
 */
public class SetWrapper implements Wrapper<Set>, Serializable {

    private Set source;

    public SetWrapper() {
    }

    public SetWrapper(Set source) {
        this();
        this.source = source;
    }

    @Override
    public String toString() {
        return "SetWrapper{" +
                "source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetWrapper that = (SetWrapper) o;
        return Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }

    @Override
    public Set getSource() {
        return source;
    }

    public void setSource(Set source) {
        this.source = source;
    }

}
