package me.junbin.commons.converter.spring.mvc;

import me.junbin.commons.util.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:52
 * @description :
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter;

    public StringToLocalDateConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public LocalDate convert(String source) {
        source = StringUtils.cleanAsEmptyString(source);
        return source.isEmpty() ? null : formatter.parse(source, LocalDate::from);
    }

}
