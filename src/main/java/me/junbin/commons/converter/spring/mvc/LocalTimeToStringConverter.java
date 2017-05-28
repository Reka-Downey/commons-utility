package me.junbin.commons.converter.spring.mvc;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:54
 * @description :
 */
public class LocalTimeToStringConverter implements Converter<LocalTime, String> {

    private final DateTimeFormatter formatter;

    public LocalTimeToStringConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public String convert(LocalTime source) {
        return null == source ? null : formatter.format(source);
    }

}
