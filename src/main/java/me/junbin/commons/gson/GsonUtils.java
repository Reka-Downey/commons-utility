package me.junbin.commons.gson;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 15:40
 * @description :
 */
public interface GsonUtils {

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
