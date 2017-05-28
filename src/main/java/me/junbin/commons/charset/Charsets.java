package me.junbin.commons.charset;

import java.nio.charset.Charset;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 0:18
 * @description :
 */
public final class Charsets {

    private Charsets() {
        throw new AssertionError("No instance of me.junbin.commons.charset.Charsets for you!");
    }

    public static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 中文编码优先推荐使用 GB18030 而不是 GBK
     * GB18030 向下兼容 GBK 和 GB2312
     */
    public static final Charset GB18030 = Charset.forName("GB18030");

    /**
     * GBK 向下兼容 GB2312
     */
    public static final Charset GBK = Charset.forName("GBK");

    public static final Charset GB2312 = Charset.forName("GB2312");

    public static final Charset ISO88591 = Charset.forName("ISO-8859-1");

}
