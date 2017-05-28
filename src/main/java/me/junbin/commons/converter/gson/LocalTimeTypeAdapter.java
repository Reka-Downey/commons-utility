package me.junbin.commons.converter.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.api.gson.GsonTypeAdapter;
import me.junbin.commons.util.Args;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016-09-09 9:06
 * @description :
 */
public class LocalTimeTypeAdapter extends GsonTypeAdapter<LocalTime> {

    private final DateTimeFormatter formatter;

    public LocalTimeTypeAdapter(final DateTimeFormatter formatter) {
        this.formatter = Args.notNull(formatter);
    }

    public LocalTimeTypeAdapter(final String pattern) {
        String notNullPattern = Args.notNull(pattern);
        this.formatter = DateTimeFormatter.ofPattern(notNullPattern.trim());
    }

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        out.value(value == null ? null : formatter.format(value));
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if (jsonToken == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalTime.parse(in.nextString(), formatter);
        }
        return null;
    }

}
