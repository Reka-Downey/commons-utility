package me.junbin.commons.converter;

import me.junbin.commons.converter.gson.LocalDateTimeTypeAdapter;
import me.junbin.commons.converter.gson.LocalDateTypeAdapter;
import me.junbin.commons.converter.gson.LocalTimeTypeAdapter;
import me.junbin.commons.converter.jpa.LocalDateConverter;
import me.junbin.commons.converter.jpa.LocalDateTimeConverter;
import me.junbin.commons.converter.jpa.LocalTimeConverter;
import me.junbin.commons.converter.mybatis.LocalDateTimeTypeHandler;
import me.junbin.commons.converter.mybatis.LocalDateTypeHandler;
import me.junbin.commons.converter.mybatis.LocalTimeTypeHandler;
import me.junbin.commons.util.Jsr310Utils;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 16:16
 * @description :
 */
public final class Converters {

    public static final class Gson {

        public static final LocalDateTimeTypeAdapter LOCAL_DATE_TIME =
                new LocalDateTimeTypeAdapter(Jsr310Utils.hyphen_yyyyMMddHHmmss);

        public static final LocalDateTypeAdapter LOCAL_DATE =
                new LocalDateTypeAdapter(Jsr310Utils.hyphen_yyyyMMdd);

        public static final LocalTimeTypeAdapter LOCAL_TIME =
                new LocalTimeTypeAdapter(Jsr310Utils.colon_HHmmss);

    }

    public static final class Jpa {

        public static final LocalDateTimeConverter LOCAL_DATE_TIME =
                new LocalDateTimeConverter();

        public static final LocalDateConverter LOCAL_DATE =
                new LocalDateConverter();

        public static final LocalTimeConverter LOCAL_TIME =
                new LocalTimeConverter();

    }

    public static final class MyBatis {

        public static final LocalDateTimeTypeHandler LOCAL_DATE_TIME =
                new LocalDateTimeTypeHandler();

        public static final LocalDateTypeHandler LOCAL_DATE =
                new LocalDateTypeHandler();

        public static final LocalTimeTypeHandler LOCAL_TIME =
                new LocalTimeTypeHandler();

    }

}
