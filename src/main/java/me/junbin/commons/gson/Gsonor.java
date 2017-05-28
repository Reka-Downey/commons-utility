package me.junbin.commons.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.junbin.commons.converter.gson.LocalDateTimeTypeAdapter;
import me.junbin.commons.converter.gson.LocalDateTypeAdapter;
import me.junbin.commons.converter.gson.LocalTimeTypeAdapter;
import me.junbin.commons.util.Jsr310Utils;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static me.junbin.commons.gson.strategy.GsonStrategies.IGNORE_FILED_EQUALS_PASSWORD;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 15:55
 * @description :
 */
public enum Gsonor implements GsonUtils {

    SIMPLE(new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalTime.class,
                    new LocalTimeTypeAdapter(Jsr310Utils.colon_HHmmss))
            .registerTypeAdapter(LocalDate.class,
                    new LocalDateTypeAdapter(Jsr310Utils.hyphen_yyyyMMdd))
            .registerTypeAdapter(LocalDateTime.class,
                    new LocalDateTimeTypeAdapter(Jsr310Utils.hyphen_yyyyMMddHHmmss))
            .create()),

    PRETTY(new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalTime.class,
                    new LocalTimeTypeAdapter(Jsr310Utils.colon_HHmmss))
            .registerTypeAdapter(LocalDate.class,
                    new LocalDateTypeAdapter(Jsr310Utils.hyphen_yyyyMMdd))
            .registerTypeAdapter(LocalDateTime.class,
                    new LocalDateTimeTypeAdapter(Jsr310Utils.hyphen_yyyyMMddHHmmss))
            .create()),

    EXCLUDE_PWD_FIELD_SIMPLE(new GsonBuilder()
            .serializeNulls()
            .setExclusionStrategies(IGNORE_FILED_EQUALS_PASSWORD)
            .registerTypeAdapter(LocalTime.class,
                    new LocalTimeTypeAdapter(Jsr310Utils.colon_HHmmss))
            .registerTypeAdapter(LocalDate.class,
                    new LocalDateTypeAdapter(Jsr310Utils.hyphen_yyyyMMdd))
            .registerTypeAdapter(LocalDateTime.class,
                    new LocalDateTimeTypeAdapter(Jsr310Utils.hyphen_yyyyMMddHHmmss))
            .create()),

    EXCLUDE_PWD_FIELD_PRETTY(new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .setExclusionStrategies(IGNORE_FILED_EQUALS_PASSWORD)
            .registerTypeAdapter(LocalTime.class,
                    new LocalTimeTypeAdapter(Jsr310Utils.colon_HHmmss))
            .registerTypeAdapter(LocalDate.class,
                    new LocalDateTypeAdapter(Jsr310Utils.hyphen_yyyyMMdd))
            .registerTypeAdapter(LocalDateTime.class,
                    new LocalDateTimeTypeAdapter(Jsr310Utils.hyphen_yyyyMMddHHmmss))
            .create());

    private final Gson gson;

    Gsonor(Gson gson) {
        this.gson = gson;
    }

    public Gson getGson() {
        return gson;
    }

    @Override
    public String toJson(Object source) {
        return this.gson.toJson(source);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return this.gson.toJson(src, typeOfSrc);
    }

    @Override
    public String toJson(JsonElement jsonElement) {
        return this.gson.toJson(jsonElement);
    }

    @Override
    public void toJson(Object src, Appendable writer) {
        this.gson.toJson(src, writer);
    }

    @Override
    public void toJson(Object src, Type typeOfSrc, Appendable writer) {
        this.gson.toJson(src, typeOfSrc, writer);
    }

    @Override
    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) {
        this.gson.toJson(src, typeOfSrc, writer);
    }

    @Override
    public void toJson(JsonElement jsonElement, Appendable writer) {
        this.gson.toJson(jsonElement, writer);
    }

    @Override
    public void toJson(JsonElement jsonElement, JsonWriter writer) {
        this.gson.toJson(jsonElement, writer);
    }

    @Override
    public JsonElement toJsonTree(Object src) {
        return this.gson.toJsonTree(src);
    }

    @Override
    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        return this.gson.toJsonTree(src, typeOfSrc);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return this.gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromJson(Reader json, Class<T> classOfT) {
        return this.gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(Reader json, Type typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromJson(JsonReader reader, Type typeOfT) {
        return this.gson.fromJson(reader, typeOfT);
    }

    @Override
    public <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return this.gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(JsonElement json, Type typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }

}
