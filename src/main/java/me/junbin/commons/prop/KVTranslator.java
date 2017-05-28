package me.junbin.commons.prop;

import me.junbin.commons.util.Args;
import me.junbin.commons.yaml.YamlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 20:46
 * @description :
 */
public class KVTranslator {

    private final Properties properties;

    /**
     * 解析 yaml 文件并生成 {@link KVTranslator}
     *
     * @param yamlPath yaml 文件路径
     * @return 属性解释器
     */
    public static KVTranslator yaml(final Path yamlPath) throws IOException {
        Path notNullYamlPath = Args.notNull(yamlPath);
        Properties properties = YamlUtils.yaml2Properties(notNullYamlPath.normalize());
        return new KVTranslator(properties);
    }

    /**
     * 解析 properties 文件并生成 {@link KVTranslator}
     *
     * @param propertiesPath properties 文件路径
     * @return 属性解释器
     */
    public static KVTranslator properties(final Path propertiesPath) throws IOException {
        Path notNullPropertiesPath = Args.notNull(propertiesPath);
        Properties properties = new Properties();
        InputStream is = Files.newInputStream(notNullPropertiesPath.normalize());
        properties.load(is);
        return new KVTranslator(properties);
    }

    public static KVTranslator properties(final InputStream propertiesStream) throws IOException {
        Args.notNull(propertiesStream);
        Properties properties = new Properties();
        properties.load(propertiesStream);
        return new KVTranslator(properties);
    }

    public KVTranslator(Properties properties) {
        this.properties = properties;
    }

    public String getAsString(final String key) {
        return getAsString(key, null);
    }

    public String getAsString(final String key, final String defaultValue) {
        Object obj = properties.get(key);
        return obj == null ? defaultValue : obj.toString();
    }

    public int getAsInt(final String key) {
        Object obj = properties.get(key);
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            throw new PropertyTranslateException(String.format("Can not parse %s --> [value of %s] as integer", obj, key));
        }
    }

    public int getAsInt(final String key, final int defaultValue) {
        try {
            return getAsInt(key);
        } catch (PropertyTranslateException e) {
            return defaultValue;
        }
    }

    public double getAsDouble(final String key) {
        Object obj = properties.get(key);
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            throw new PropertyTranslateException(String.format("Can not parse %s --> [value of %s] as double", obj, key));
        }
    }

    public double getAsDouble(final String key, final double defaultValue) {
        try {
            return getAsDouble(key);
        } catch (PropertyTranslateException e) {
            return defaultValue;
        }
    }

    public boolean getAsBoolean(final String key) {
        Object obj = properties.get(key);
        try {
            return Boolean.parseBoolean(obj.toString().trim());
        } catch (NullPointerException e) {
            throw new PropertyTranslateException(String.format("The property %s is undefined!", key));
        }
    }

    public boolean getAsBoolean(final String key, final boolean defaultValue) {
        try {
            return getAsBoolean(key);
        } catch (PropertyTranslateException e) {
            return defaultValue;
        }
    }

}
