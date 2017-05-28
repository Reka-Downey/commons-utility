package me.junbin.commons.converter.custom.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 16:05
 * @description :
 */
public interface GsonTypeAdapter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
