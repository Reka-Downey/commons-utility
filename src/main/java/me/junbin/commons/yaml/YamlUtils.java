package me.junbin.commons.yaml;

import me.junbin.commons.util.Args;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 0:19
 * @description : 建议 yml 文件只使用 Hash 结构，尽量不使用 List 结构。
 * 并且不要在单个 yml 文件中定义多个 yaml 文档（即不使用文档分隔符）。
 * Hash 结构建议如下：
 * <pre>
 *      {@code
 *      user:
 *          name: Reka
 *          age: 24
 *
 *      db:
 *          url: jdbc:mysql:///dbName
 *          driverClassName: com.mysql.jdbc.Driver
 *          username: root
 *          password:
 *      }
 * </pre>
 * 建议不使用的语法有：& 锚符；* 锚定位符；| 保留换行符；> 折叠换行符；区块嵌套等。
 * 行末不可以使用 # 注释。
 */
public abstract class YamlUtils {

    /**
     * 将 yml 文件解析成 {@link java.util.LinkedHashMap} 对象，其中 Key 都是 {@link String}
     * 类型，V 最好也声明为 {@link Object} 类型，否则遇到非 String 类型的数据将会造成 {@link
     * ClassCastException}。
     * 例如：上面的 yml 内容被解析成如下：
     * <pre>
     * {@code
     *      user: {
     *          name: "Reka",
     *          age: 24
     *      },
     *      db: {
     *          url: "jdbc:mysql:///dbName",
     *          driverClassName: "com.mysql.jdbc.Driver",
     *          username: "root",
     *          password: null
     *      }
     * }
     * </pre>
     * 这里将 password 解析成 {@code null} 某种程度上来说是错误的。
     *
     * @param yamlSource yml 文件路径
     * @return yml 对应的 {@link Map}
     */
    public static Map<String, Object> yaml2Map(final Path yamlSource) throws IOException {
        Path notNullYamlSource = Args.notNull(yamlSource);
        YamlMap yamlMap = new YamlMap(notNullYamlSource);
        return yamlMap.getObject();
    }

    /**
     * 将 yml 文件解析成 {@link java.util.Properties} 对象，其中 Key 都是 {@link String}
     * 类型，V 都是 {@link Object} 类型。
     * 例如：上面的 yml 内容被解析成如下：
     * <pre>
     * {@code
     *      user.name = "Reka"
     *      user.age = 24
     *      db.url = "jdbc:mysql:///dbName"
     *      db.driverClassName = "com.mysql.jdbc.Driver"
     *      db.username = "root"
     *      db.password = ""
     * }
     * @param yamlSource yml 文件路径
     * @return yml 对应的 {@link Properties}
     */
    public static Properties yaml2Properties(final Path yamlSource) throws IOException {
        Path notNullYamlSource = Args.notNull(yamlSource);
        YamlProperties yamlProperties = new YamlProperties(notNullYamlSource);
        return yamlProperties.getObject();
    }

}
