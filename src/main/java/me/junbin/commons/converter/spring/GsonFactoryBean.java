package me.junbin.commons.converter.spring;

import com.google.gson.Gson;
import me.junbin.commons.gson.Gsonor;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 16:15
 * @description :
 */
public class GsonFactoryBean implements FactoryBean<Gson> {

    @Override
    public Gson getObject() throws Exception {
        return Gsonor.SN_SIMPLE.getGson();
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
