package me.junbin.commons.converter.spring.mvc;

import me.junbin.commons.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:43
 * @description :从前台的 {@link String} 传到后台时若被指定为 {@link LocalDateTime} 类型，
 * 则会调用这个转换器进行转换。
 * 　　之后通过 {@link org.springframework.context.support.ConversionServiceFactoryBean#converters}
 * 属性将该 {@link Converter} 添加进去，后续就会自动进行类型转换了。
 * 　　另一种简单的方法时不使用这个转换器，通过在 {@link org.springframework.stereotype.Controller}
 * 中的方法的入参中使用 {@link DateTimeFormat#pattern()} 注解指明日期格式，Spring4 之后将会自动转成
 * {@link LocalDateTime}
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter;

    public StringToLocalDateTimeConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public LocalDateTime convert(String source) {
        source = StringUtils.cleanAsEmptyString(source);
        return source.isEmpty() ? null : LocalDateTime.parse(source, formatter);
    }

}
