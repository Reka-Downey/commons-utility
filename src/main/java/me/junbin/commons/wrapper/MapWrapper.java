package me.junbin.commons.wrapper;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/12/31 9:05
 * @description :
 */
public class MapWrapper implements Wrapper<Map>, Serializable {

    private Map source;

    public MapWrapper() {
    }

    public MapWrapper(Map source) {
        this();
        this.source = source;
    }

    @Override
    public String toString() {
        return "MapWrapper{" +
                "source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapWrapper that = (MapWrapper) o;
        return Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }

    @Override
    public Map getSource() {
        return source;
    }

    public void setSource(Map source) {
        this.source = source;
    }

}
