package me.junbin.commons.yaml;

import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 22:28
 * @description :
 */
public class YamlProperties extends YamlProcessor<Properties> {

    private Properties properties;

    public YamlProperties(Path... resources) {
        this.setResources(resources);
        this.properties = createProperties();
    }

    @Override
    public Properties getObject() {
        return this.properties != null ? this.properties : createProperties();
    }

    protected Properties createProperties() {
        final Properties result = new Properties();
        process(new MatchCallback() {
            @Override
            public void process(Properties properties, Map<String, Object> map) {
                result.putAll(properties);
            }
        });
        return result;
    }

}
