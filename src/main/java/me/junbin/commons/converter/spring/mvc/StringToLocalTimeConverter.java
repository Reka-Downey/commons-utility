package me.junbin.commons.converter.spring.mvc;

import me.junbin.commons.util.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:55
 * @description :
 */
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private final DateTimeFormatter formatter;

    public StringToLocalTimeConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public LocalTime convert(String source) {
        source = StringUtils.cleanAsEmptyString(source);
        return source.isEmpty() ? null : LocalTime.parse(source, formatter);
    }

}
