package me.junbin.commons.util;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/24 20:41
 * @description :
 */
public abstract class StringUtils {

    private static final String EMPTY_STRING = "";
    // 匹配所有的全角符号，不仅仅包括标点符号，还包括全角字母和全角数字
    private static final String fullWidth = "[\uFE30-\uFFA0]";
    // [\u4E00-\u9FCC]
    // 不建议使用 Unicode 编码 [\u4E00-\u9FCC] 范围来判断汉字；因为这个方法不准确，还有很多汉字不在这个范围之内。比如：㐀㐂㐄
    private static final Pattern fullWidthPattern = Pattern.compile(fullWidth);


    public static boolean isEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    public static boolean notEmpty(final String text) {
        return !isEmpty(text);
    }

    public static boolean isNull(final String text) {
        return text == null;
    }

    public static boolean notNull(final String text) {
        return !isNull(text);
    }

    public static Optional<String> clean(final String text) {
        if (text == null) {
            return Optional.empty();
        }
        String trim = text.trim();
        if (trim.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(trim);
    }

    public static String cleanAsEmptyString(final String text) {
        if (text == null) {
            return EMPTY_STRING;
        }
        return text.trim();
    }

    /**
     * Returns true if the parameter is null or contains only whitespace
     *
     * @param text 字符串文本
     * @return 如果 {@code text} 是 {@code null} 或者只包含空白字符，那么返回 {@code true}，
     * 否则返回 {@code false}
     */
    public static boolean isBlank(final String text) {
        return isBlank(text, true);
    }

    /**
     * @param text        字符串文本
     * @param nullAsBlank 当字符串为 {@code null} 的时候是否记为空串
     * @return 如果 {@code text} 只包含空白字符，那么返回 {@code true}；如果 {@code text} 是
     * {@code null} 则返回 {@code nullAsBlank}；否则返回 {@code false}；
     */
    public static boolean isBlank(final String text, final boolean nullAsBlank) {
        if (text == null) {
            return nullAsBlank;
        }
        for (int i = 0, len = text.length(); i < len; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsBlanks(final CharSequence text) {
        return containsBlanks(text, false);
    }

    public static boolean containsBlanks(final CharSequence text, final boolean nullAsBlank) {
        if (text == null) {
            return nullAsBlank;
        }
        for (int i = 0, len = text.length(); i < len; i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String replaceFullWidthPunctuation(final String text) {
        return replaceFullWidthPunctuation(text, EMPTY_STRING);
    }

    public static String replaceFullWidthPunctuation(final String text, final String replacement) {
        return fullWidthPattern.matcher(text).replaceAll(replacement);
    }


/*
    //使用 Jdk6 的静态内部类 {@link Character.UnicodeBlock} 判断
    //@param c 字符
    //@return 如果入参字符是中文（中日韩）字符，那么返回{@code true}，否则返回{@code false}
    public static boolean isChineseUsingJdk6(char c) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(c);
        return unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
    }
*/

    /**
     * 使用 Jdk7 判断字符是否是中文字符
     *
     * @param c 字符
     * @return 如果入参字符是中文（中日韩）字符，那么返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isChinese(final char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        return sc == Character.UnicodeScript.HAN;
    }

    /**
     * 根据 UnicodeBlock 方法判断中文标点符号
     *
     * @param c 字符
     * @return 如果入参字符是中文标点符号，那么返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(c);
        return unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || unicodeBlock == Character.UnicodeBlock.VERTICAL_FORMS;
    }

}
