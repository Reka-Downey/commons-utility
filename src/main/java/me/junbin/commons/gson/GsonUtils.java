package me.junbin.commons.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.Converters;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 15:40
 * @description :
 */
public interface GsonUtils {

    GsonBuilder JSR_310_BUILDER =
            new GsonBuilder()
                    .registerTypeAdapter(LocalTime.class, Converters.Gson.LOCAL_TIME)
                    .registerTypeAdapter(LocalDate.class, Converters.Gson.LOCAL_DATE)
                    .registerTypeAdapter(LocalDateTime.class, Converters.Gson.LOCAL_DATE_TIME);

    String toJson(Object source);

    String toJson(Object src, Type typeOfSrc);

    String toJson(JsonElement jsonElement);

    void toJson(Object src, Appendable writer);

    void toJson(Object src, Type typeOfSrc, Appendable writer);

    void toJson(Object src, Type typeOfSrc, JsonWriter writer);

    void toJson(JsonElement jsonElement, Appendable writer);

    void toJson(JsonElement jsonElement, JsonWriter writer);

    JsonElement toJsonTree(Object src);

    JsonElement toJsonTree(Object src, Type typeOfSrc);

    <T> T fromJson(String json, Class<T> classOfT);

    <T> T fromJson(String json, Type typeOfT);

    <T> T fromJson(Reader json, Class<T> classOfT);

    <T> T fromJson(Reader json, Type typeOfT);

    <T> T fromJson(JsonReader reader, Type typeOfT);

    <T> T fromJson(JsonElement json, Class<T> classOfT);

    <T> T fromJson(JsonElement json, Type typeOfT);

}
