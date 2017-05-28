package me.junbin.commons.converter.gson;

import com.google.gson.*;
import me.junbin.commons.converter.custom.gson.GsonTypeAdapter;
import me.junbin.commons.util.Args;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:05
 * @description :
 */
public class LocalDateTypeAdapter implements GsonTypeAdapter<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateTypeAdapter(final DateTimeFormatter formatter) {
        this.formatter = Args.notNull(formatter);
    }

    public LocalDateTypeAdapter(final String pattern) {
        String notNullPattern = Args.notNull(pattern);
        this.formatter = DateTimeFormatter.ofPattern(notNullPattern.trim());
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return null == src ? null : new JsonPrimitive(formatter.format(src));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null == json ? null : LocalDate.parse(json.getAsString(), formatter);
    }

}
