package me.junbin.commons.converter.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.api.gson.GsonTypeAdapter;
import me.junbin.commons.util.Args;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:05
 * @description :
 */
public class LocalDateTypeAdapter extends GsonTypeAdapter<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateTypeAdapter(final DateTimeFormatter formatter) {
        this.formatter = Args.notNull(formatter);
    }

    public LocalDateTypeAdapter(final String pattern) {
        String notNullPattern = Args.notNull(pattern);
        this.formatter = DateTimeFormatter.ofPattern(notNullPattern.trim());
    }

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value == null ? null : formatter.format(value));
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if (jsonToken == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDate.parse(in.nextString(), formatter);
        }
        return null;
    }

}
