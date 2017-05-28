package me.junbin.commons.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/24 21:14
 * @description :
 */
public abstract class Args {

    public static void check(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("expression must be true");
        }
    }

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    public static <T> T notNull(final T argument) {
        if (argument == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        return argument;
    }

    public static <T> T notNull(final T argument, final String message) {
        if (argument == null) {
            throw new IllegalArgumentException(message);
        }
        return argument;
    }

    public static String notEmpty(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("text must not be empty");
        }
        return text;
    }

    public static String notEmpty(final String text, final String message) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    public static String notBlank(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("text must not be blank");
        }
        return text;
    }

    public static String notBlank(final String text, final String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    public static String containsNoBlanks(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (StringUtils.containsBlanks(text)) {
            throw new IllegalArgumentException("text must not contain blanks");
        }
        return text;
    }

    public static <E, T extends Collection<E>> T notEmpty(final T collection) {
        if (collection == null) {
            throw new IllegalArgumentException("collection must not be null");
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("collection must not be empty");
        }
        return collection;
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(final T map) {
        if (map == null) {
            throw new IllegalArgumentException("map must not be null");
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException("map must not be empty");
        }
        return map;
    }

    public static int positive(final int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("num must not be negative or zero");
        }
        return num;
    }

    public static long positive(final long num) {
        if (num <= 0) {
            throw new IllegalArgumentException("num must not be negative or zero");
        }
        return num;
    }

    public static int notNegative(final int num) {
        if (num < 0) {
            throw new IllegalArgumentException("num must not be negative");
        }
        return num;
    }

    public static long notNegative(final long num) {
        if (num < 0) {
            throw new IllegalArgumentException("num must not be negative");
        }
        return num;
    }

}