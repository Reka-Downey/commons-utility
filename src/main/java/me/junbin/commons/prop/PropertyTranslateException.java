package me.junbin.commons.prop;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 20:52
 * @description :
 */
public class PropertyTranslateException extends RuntimeException {

    public PropertyTranslateException() {
    }

    public PropertyTranslateException(String message) {
        super(message);
    }

    public PropertyTranslateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyTranslateException(Throwable cause) {
        super(cause);
    }

}
