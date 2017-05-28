package me.junbin.commons.gson.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.regex.Pattern;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 15:41
 * @description :
 */
public final class GsonStrategies {

    /**
     * 只忽略名称为 password 的字段
     */
    public static final ExclusionStrategy IGNORE_FILED_EQUALS_PASSWORD = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            //return f.getDeclaredType() == String.class &&
            //        "password".equals(f.getName());
            return "password".equals(f.getName());
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    /**
     * 忽略大小写匹配所有名称中包含 password 或者 pwd 的字段
     */
    public static final ExclusionStrategy IGNORE_PWD_FILED_BY_REGEX = new ExclusionStrategy() {
        private final Pattern passwordPattern = Pattern.compile(".*password.*|.*pwd.*", Pattern.CASE_INSENSITIVE);

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return passwordPattern.matcher(f.getName()).matches();
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

}
