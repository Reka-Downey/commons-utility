package me.junbin.commons.charset;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import me.junbin.commons.util.Args;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/27 9:17
 * @description : 字符编码检测器
 * 通过 {@link CharsetMatch#getName()} 可以得到检测出来的字符编码；
 * 通过 {@link CharsetMatch#getConfidence()} ()} 可以得到对该字符编码；
 * 通过 {@link CharsetMatch#getName()} 可以得到检测出来的字符编码；
 */
public abstract class Detectors {

    public static CharsetMatch detect(final byte[] data) {
        Args.notNull(data);
        return new CharsetDetector().setText(data).detect();
    }

    public static CharsetMatch detect(final InputStream data) throws IOException {
        Args.notNull(data);
        return new CharsetDetector().setText(data).detect();
    }

    public static CharsetMatch[] detectAll(final byte[] data) {
        Args.notNull(data);
        return new CharsetDetector().setText(data).detectAll();
    }

    public static CharsetMatch[] detectAll(final InputStream data) throws IOException {
        Args.notNull(data);
        return new CharsetDetector().setText(data).detectAll();
    }

}
