package me.junbin.commons.charset;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 0:18
 * @description : 原生的 {@link Charset#forName(String)} 内部所使用的查找算法效率最高
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

    private static final Map<String, Charset> cache = new HashMap<>();

/*
    static {
        for (String alias : UTF8.aliases()) {
            cache.put(alias, UTF8);
        }
        for (String alias : GB18030.aliases()) {
            cache.put(alias, GB18030);
        }
        for (String alias : GBK.aliases()) {
            cache.put(alias, GBK);
        }
        for (String alias : GB2312.aliases()) {
            cache.put(alias, GB2312);
        }
        for (String alias : ISO88591.aliases()) {
            cache.put(alias, ISO88591);
        }
    }

    public static Charset get(final String charsetName) {
        String name = Args.notNull(charsetName);
        Charset cached = cache.get(name);
        if (cached != null) {
            return cached;
        }
        Charset charset = Charset.forName(charsetName);
        for (String alias : charset.aliases()) {
            cache.put(alias, charset);
        }
        return charset;
    }
*/

}
