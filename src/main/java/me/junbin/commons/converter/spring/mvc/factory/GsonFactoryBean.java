package me.junbin.commons.converter.spring.mvc.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.junbin.commons.converter.gson.LocalDateTimeTypeAdapter;
import me.junbin.commons.converter.gson.LocalDateTypeAdapter;
import me.junbin.commons.converter.gson.LocalTimeTypeAdapter;
import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/11/10 21:09
 * @description : 使用 {@link FactoryBean} 来生成 {@link Gson}，
 * 用来替换 {@link org.springframework.http.converter.json.GsonHttpMessageConverter#gson}
 */
public class GsonFactoryBean implements FactoryBean<Gson> {

    private static class GsonHolder {
        private static final Gson GSON = new GsonBuilder().serializeNulls()
                                                          .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter("HH:mm:ss"))
                                                          .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter("yyyy-MM-dd"))
                                                          .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter("yyyy-MM-dd HH:mm:ss"))
                                                          .create();
    }

    @Override
    public Gson getObject() throws Exception {
        return GsonHolder.GSON;
    }

    @Override
    public Class<?> getObjectType() {
        return Gson.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
