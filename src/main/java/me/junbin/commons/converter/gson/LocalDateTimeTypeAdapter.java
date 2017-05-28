package me.junbin.commons.converter.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.api.gson.GsonTypeAdapter;
import me.junbin.commons.util.Args;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:01
 * @description :
 */
public class LocalDateTimeTypeAdapter extends GsonTypeAdapter<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeTypeAdapter(final DateTimeFormatter formatter) {
        this.formatter = Args.notNull(formatter);
    }

    public LocalDateTimeTypeAdapter(final String pattern) {
        String notNullPattern = Args.notNull(pattern);
        this.formatter = DateTimeFormatter.ofPattern(notNullPattern.trim());
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value == null ? null : formatter.format(value));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if (jsonToken == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDateTime.parse(in.nextString(), formatter);
        }
        return null;
    }

}
