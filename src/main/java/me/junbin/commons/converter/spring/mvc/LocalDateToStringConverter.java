package me.junbin.commons.converter.spring.mvc;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:51
 * @description :
 */
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    private final DateTimeFormatter formatter;

    public LocalDateToStringConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public String convert(LocalDate source) {
        return null == source ? null : formatter.format(source);
    }

}
