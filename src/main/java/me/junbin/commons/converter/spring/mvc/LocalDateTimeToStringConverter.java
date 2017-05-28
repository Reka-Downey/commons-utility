package me.junbin.commons.converter.spring.mvc;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:36
 * @description : 将从 {@link org.springframework.stereotype.Controller} 后台通过
 * {@link org.springframework.web.servlet.ModelAndView} 传到前台的 {@link LocalDateTime}
 * 转换成 {@link String}。
 * 　　之后通过 {@link org.springframework.context.support.ConversionServiceFactoryBean#converters}
 * 属性将该 {@link Converter} 添加进去，后续就会自动进行类型转换了
 */
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    private final DateTimeFormatter formatter;

    // 这里无法通过 xml 传入 DateTimeFormatter，因为它的执行顺序高于我们注册的 propertyEditor
    public LocalDateTimeToStringConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern.trim());
    }

    @Override
    public String convert(LocalDateTime source) {
        return null == source ? null : formatter.format(source);
    }

}
