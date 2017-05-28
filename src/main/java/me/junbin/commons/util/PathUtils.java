package me.junbin.commons.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 14:21
 * @description :
 */
public abstract class PathUtils {

    public static String slashPath(final Path path) {
        String pathString = Args.notNull(path).normalize().toString();
        return pathString.replaceAll("\\\\+", "/");
    }

    /**
     * 获取文件的后缀名
     * <pre>
     *     PathUtils.extensionName(Paths.get("/user/local/catalina.log")) = .log
     *     PathUtils.extensionName(Paths.get("/user/local/catalina.txt")) = .txt
     *     PathUtils.extensionName(Paths.get("/user/local/dir.bak/file")) = IllegalArgumentException
     *     PathUtils.extensionName(Paths.get("/user/local/catalina/log")) = IllegalArgumentException
     * </pre>
     *
     * @param path 文件路径对象
     * @return 文件后缀名
     * @throws IllegalArgumentException 文件后缀名格式不正确
     */
    public static String extensionName(final Path path) {
        String pathString = slashPath(path);
        int extIndex = pathString.lastIndexOf('.');
        if (extIndex == -1) {
            throw new IllegalArgumentException(String.format("Path{%s} does not contains '.' character", pathString));
        }
        String ext = pathString.substring(extIndex);
        if (ext.indexOf('/') != -1) {
            throw new IllegalArgumentException(String.format("The ext{%s} is not allowed", ext));
        }
        return ext;
    }

    /**
     * 获取 classpath 下的资源文件
     *
     * @param resource 资源位置
     * @return 资源文件路径
     * @throws IllegalArgumentException 资源不存在
     */
    public static Path classpath(final String resource) {
        Args.notNull(resource);
        URL url = Thread.currentThread().getContextClassLoader()
                        .getResource(resource.trim());
        if (url == null) {
            throw new IllegalArgumentException(String.format("No Such resource{%s} under the classpath", resource));
        }
        try {
            // 通过 URL#getFile 或者 URL#getPath 无法获取 Path
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Can not resolve the path{%s}", url), e);
        }
    }

}
