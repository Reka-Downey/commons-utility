package me.junbin.commons.yaml;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 22:26
 * @description :
 */
public class YamlMap extends YamlProcessor<Map> {

    private Map<String, Object> map;

    public YamlMap(Path... resources) {
        this.setResources(resources);
        this.map = createMap();
    }

    @Override
    public Map<String, Object> getObject() {
        return this.map != null ? this.map : createMap();
    }

    protected Map<String, Object> createMap() {
        final Map<String, Object> result = new LinkedHashMap<>();
        process(new MatchCallback() {
            @Override
            public void process(Properties properties, Map<String, Object> map) {
                merge(result, map);
            }
        });
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void merge(Map<String, Object> output, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Object existing = output.get(key);
            if (value instanceof Map && existing instanceof Map) {
                Map<String, Object> result = new LinkedHashMap<>((Map) existing);
                merge(result, (Map) value);
                output.put(key, result);
            } else {
                output.put(key, value);
            }
        }
    }

}
