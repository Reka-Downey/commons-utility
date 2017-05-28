package me.junbin.commons.converter.spring;

import me.junbin.commons.util.Args;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:32
 * @description :
 */
public class LocalDatePropertyEditor extends PropertyEditorSupport {

    private final DateTimeFormatter formatter;

    public LocalDatePropertyEditor(final DateTimeFormatter formatter) {
        this.formatter = Args.notNull(formatter);
    }

    public LocalDatePropertyEditor(final String pattern) {
        String notNullPattern = Args.notNull(pattern);
        this.formatter = DateTimeFormatter.ofPattern(notNullPattern.trim());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(LocalDate.parse(text.trim(), formatter));
    }

}
