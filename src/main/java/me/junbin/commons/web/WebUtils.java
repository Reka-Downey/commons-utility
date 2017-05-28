package me.junbin.commons.web;

import me.junbin.commons.util.CollectionUtils;
import org.springframework.http.HttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/31 19:21
 * @description : 这个类只建议在 Web 环境下使用，并且必须在拥有 Http 数据的 Controller 层中使用，
 * 在 Service、Repository 等非 Web 层中使用的话所有的话必须携带 ServletContext。或者保证 Service、
 * Repository 层的调用都是由 Controller 层发起的。
 */
public class WebUtils {


    /**
     * Standard Servlet spec context attribute that specifies a temporary
     * directory for the current web application, of type {@code java.io.File}.
     */
    public static final String TEMP_DIR_CONTEXT_ATTRIBUTE = "javax.servlet.context.tempdir";

    /**
     * 获取当前线程所属的 {@link WebApplicationContext}，此方法需要当前线程拥有 Spring Web
     * 应用程序的 {@link ClassLoader}（即必须在 Spring Web 环境下才能获取得到）。
     *
     * @return 如果当前线程并不属于 Spring Web 应用程序，那么返回 {@code null}；否则返回
     * 当前 Web 应用程序的 {@link WebApplicationContext}
     * 。
     */
    public static WebApplicationContext currentWebAppCtx() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

    public static WebApplicationContext currentWebAppCtx(ServletContext servletContext) {
        return WebApplicationContextUtils.findWebApplicationContext(servletContext);
    }

    /**
     * 基于 Bean 的类型来检索 Spring Bean
     *
     * @param requiredType Bean 的类型
     * @param <T>          泛型
     * @return 如果 Spring 容器中存在唯一一个指定类型的 Bean，那么返回该 Bean，否则抛出异常
     */
    public static <T> T getBean(Class<T> requiredType) {
        WebApplicationContext wac = currentWebAppCtx();
        if (wac == null) {
            throw new IllegalStateException();
        }
        return wac.getBean(requiredType);
    }

    public static <T> T getBean(ServletContext servletContext, Class<T> requiredType) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        WebApplicationContext wac = currentWebAppCtx(servletContext);
        if (wac == null) {
            throw new IllegalStateException();
        }
        return wac.getBean(requiredType);
    }

    /**
     * 基于 Bean 的名称进行检索，同时限定 Bean 的类型，如果检索不到 Bean 或者检索到的 Bean 的类型不匹配则抛出异常
     *
     * @param name         Bean 的名称
     * @param requiredType Bean 的类型
     * @param <T>          泛型
     * @return 如果 Spring 容器中存在指定名称的 Bean 并且 Bean 类型与指定的类型匹配，那么返回该 Bean，否则抛出异常
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        WebApplicationContext wac = currentWebAppCtx();
        if (wac == null) {
            throw new IllegalArgumentException();
        }
        return wac.getBean(name, requiredType);
    }

    public static <T> T getBean(ServletContext servletContext, String name, Class<T> requiredType) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        WebApplicationContext wac = currentWebAppCtx(servletContext);
        if (wac == null) {
            throw new IllegalArgumentException();
        }
        return wac.getBean(name, requiredType);
    }

    /**
     * 获取当前线程所属的 {@link ServletContext}，此方法需要当前线程在 Spring Web 环境下才能生效。
     *
     * @return 如果当前线程不在 Spring Web 环境中，那么返回 {@code null}，否则返回当前 Web 应用程序
     * 的 {@link ServletContext}
     */
    public static ServletContext currentServletContext() {
        WebApplicationContext webAppCtx = currentWebAppCtx();
        return null == webAppCtx ? null : webAppCtx.getServletContext();
    }

/*
    // 可能抛出 IllegalArgumentException
    public static String getWebRootPath() {
        return getWebRootPath(currentServletContext());
    }

    // 可能抛出 IllegalArgumentException
    public static String getWebRootPath(ServletContext servletContext) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        return servletContext.getRealPath("/");
    }

    // 可能为 null
    public static String getRealPath(String webPath) {
        return getRealPath(currentServletContext(), webPath);
    }

    // 可能为 null
    public static String getRealPath(ServletContext servletContext, String webPath) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        if (!webPath.startsWith("/")) {
            webPath = "/" + webPath;
        }
        return servletContext.getRealPath(webPath);
    }

    // 可能抛出 IllegalArgumentException
    public static String getRealPath(String webPath, boolean createIfAbsent) throws IOException {
        return getRealPath(currentServletContext(), webPath, createIfAbsent);
    }

    // 可能抛出 IllegalArgumentException
    public static String getRealPath(ServletContext servletContext, String webPath, boolean createIfAbsent) throws IOException {
        String path = getRealPath(servletContext, webPath);
        if (path == null) {
            if (createIfAbsent) {
                Path realPath = Files.createDirectories(Paths.get(getWebRootPath(servletContext), webPath).normalize());
                return realPath.toString();
            }
        }
        return null;
    }

    // 可能为 null
    public static String getRealFile(String webFilePath) {
        return getRealFile(currentServletContext(), webFilePath);
    }

    // 可能为 null
    public static String getRealFile(ServletContext servletContext, String webFilePath) {
        return getRealPath(servletContext, webFilePath);
    }

    public static String getRealFile(String webFilePath, boolean createIfAbsent) throws IOException {
        return getRealFile(currentServletContext(), webFilePath, createIfAbsent);
    }

    public static String getRealFile(ServletContext servletContext, String webFilePath, boolean createIfAbsent) throws IOException {
        String filePath = getRealFile(servletContext, webFilePath);
        if (null == filePath) {
            if (createIfAbsent) {
                Path path = Files.createFile(Paths.get(getWebRootPath(servletContext), webFilePath).normalize());
                return path.toString();
            }
        }
        return null;
    }
*/

    /**
     * 获取当前线程所属应用的 webapp 的根目录，此方法需要当前线程在 Spring Web 环境下才能生效。
     *
     * @return 当前应用的 webapp 根目录的绝对路径
     * @throws IllegalArgumentException 当前线程不属于 Spring Web 应用程序
     */
    public static Path getWebRootPath() {
        return getWebRootPath(currentServletContext());
    }

    /**
     * 获取指定 {@link ServletContext} 的 webapp 根目录的绝对路径
     *
     * @param servletContext 指定 Web 应用的 {@link ServletContext}
     * @return 指定 {@link ServletContext} 的 webapp 根目录的绝对路径
     * @throws IllegalArgumentException {@code ServletContext} 为 {@code null}，或者无法获取 Web 应用根目录
     */
    public static Path getWebRootPath(ServletContext servletContext) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        String pathString = servletContext.getRealPath("/");
        Path path = Paths.get(pathString).normalize();
        Assert.isTrue(Files.exists(path), "Web app root is not available /*accessible*/");
        return path;
    }

    /**
     * 基于当前线程的 Spring Web 应用，获取相对于 webapp 根目录的目录的绝对路径
     *
     * @param webPath webapp 根目录下，目录的相对路径
     * @return 相对于 webapp 根目录的目录的绝对路径，如果该目录不存在则返回 {@code null}
     * @throws IllegalArgumentException 当前线程的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealPath(String webPath) {
        return getRealPath(currentServletContext(), webPath);
    }

    /**
     * 获取指定 {@code ServletContext} 下，相对于 webapp 根目录的目录的绝对路径
     *
     * @param servletContext 指定 Web 应用的 {@link ServletContext}
     * @param webPath        webapp 根目录下，目录的相对路径
     * @return 相对于 webapp 根目录的目录的绝对路径，如果该目录不存在则返回 {@code null}
     * @throws IllegalArgumentException 指定的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealPath(ServletContext servletContext, String webPath) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        if (!webPath.startsWith("/")) {
            webPath = "/" + webPath;
        }
        String pathString = servletContext.getRealPath(webPath);
        Path path = Paths.get(pathString).normalize();
        return Files.exists(path) ? path : null;
    }

    /**
     * 基于当前线程的 Spring Web 应用，获取相对于 webapp 根目录下的指定目录的绝对路径
     *
     * @param webPath        webapp 根目录下，目录的相对路径
     * @param createIfAbsent 当目录不存在时，是否自动创建
     * @return 如果指定 webapp 相对目录存在或者 {@code createIfAbsent} 为 {@code true}，那么返回该路径
     * 的绝对路径，否则返回 {@code null}
     * @throws IOException              无法创建目录
     * @throws IllegalArgumentException 当前线程的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealPath(String webPath, boolean createIfAbsent) throws IOException {
        return getRealPath(currentServletContext(), webPath, createIfAbsent);
    }

    /**
     * 获取相对于 webapp 根目录下的指定目录的绝对路径
     *
     * @param servletContext 指定 {@code ServletContext} 环境
     * @param webPath        webapp 根目录下，目录的相对路径
     * @param createIfAbsent 当目录不存在时，是否自动创建
     * @return 如果指定 webapp 相对目录存在或者 {@code createIfAbsent} 为 {@code true}，那么返回该路径的绝对路径，
     * 否则返回 {@code null}
     * @throws IOException              无法创建目录
     * @throws IllegalArgumentException 指定的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealPath(ServletContext servletContext, String webPath, boolean createIfAbsent)
            throws IOException {
        Path path = getRealPath(servletContext, webPath);
        if (path == null) {
            if (createIfAbsent) {
                if (webPath.startsWith("/")) {
                    webPath = webPath.substring(1);
                }
                return Files.createDirectories(getWebRootPath(servletContext).resolve(webPath).normalize());
            }
        }
        return null;
    }

    /**
     * 基于当前线程的 Spring Web 应用，获取相对于 webapp 根目录的文件的绝对路径
     *
     * @param webFilePath webapp 根目录下，文件的相对路径
     * @return 相对于 webapp 根目录的文件的绝对路径，如果该文件不存在则返回 {@code null}
     * @throws IllegalArgumentException 当前线程的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealFile(String webFilePath) {
        return getRealFile(currentServletContext(), webFilePath);
    }

    /**
     * 获取指定 {@code ServletContext} 下，相对于 webapp 根目录的目录的绝对路径
     *
     * @param servletContext 指定 Web 应用的 {@link ServletContext}
     * @param webFilePath    webapp 根目录下，文件的相对路径
     * @return 相对于 webapp 根目录的文件的绝对路径，如果该文件不存在则返回 {@code null}
     * @throws IllegalArgumentException 指定的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealFile(ServletContext servletContext, String webFilePath) {
        return getRealPath(servletContext, webFilePath);
    }

    /**
     * 基于当前线程的 Spring Web 应用，获取相对于 webapp 根目录下的文件的绝对路径
     *
     * @param webFilePath    webapp 根目录下，文件的相对路径
     * @param createIfAbsent 当文件不存在时，是否自动创建
     * @return 如果指定 webapp 根目录下的文件存在或者 {@code createIfAbsent} 为 {@code true}，那么返回该路
     * 径的绝对路径，否则返回 {@code null}
     * @throws IOException              无法创建文件所在的目录或者无法创建文件
     * @throws IllegalArgumentException 当前线程的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealFile(String webFilePath, boolean createIfAbsent) throws IOException {
        return getRealFile(currentServletContext(), webFilePath, createIfAbsent);
    }

    /**
     * 获取相对于 webapp 根目录下的文件的绝对路径
     *
     * @param servletContext 指定 {@code ServletContext} 环境
     * @param webFilePath    根目录下，文件的相对路径
     * @param createIfAbsent 当文件不存在时，是否自动创建
     * @return 如果指定 webapp 根目录下的文件存在或者 {@code createIfAbsent} 为 {@code true}，那么返回该路
     * 径的绝对路径，否则返回 {@code null}
     * @throws IOException              无法创建文件所在的目录或者无法创建文件
     * @throws IllegalArgumentException 指定的 {@code ServletContext} 为 {@code null}
     */
    public static Path getRealFile(ServletContext servletContext, String webFilePath, boolean createIfAbsent) throws IOException {
        Path filePath = getRealFile(servletContext, webFilePath);
        if (null == filePath) {
            if (createIfAbsent) {
                if (webFilePath.startsWith("/")) {
                    webFilePath = webFilePath.substring(1);
                }
                Path path = getWebRootPath(servletContext).resolve(webFilePath).normalize();
                @SuppressWarnings("unused") Path dir = Files.createDirectories(path.getParent());
                return Files.createFile(path);
            }
        }
        return null;
    }


    /**
     * 获取指定类型的 {@code ServletRequest} 对象，如果有必要的话，将会对 {@code ServletRequest} 对象执行解包操作
     *
     * @param request      需要处理的 {@code ServletRequest} 对象
     * @param requiredType 想要获取的 {@code ServletRequest} 类型
     * @return 如果 {@code ServletRequest} 可以匹配指定的类型，那么返回该类型的 {@code
     * ServletRequest}，否则返回 {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNativeRequest(ServletRequest request, Class<T> requiredType) {
        if (requiredType != null) {
            if (requiredType.isInstance(request)) {
                return (T) request;
            } else if (request instanceof ServletRequestWrapper) {
                return getNativeRequest(((ServletRequestWrapper) request).getRequest(), requiredType);
            }
        }
        return null;
    }

    /**
     * 获取指定类型的 {@code ServletResponse} 对象，如果有必要的话，将会对 {@code ServletResponse} 对象执行解包操作
     *
     * @param response     需要处理的 {@code ServletResponse} 对象
     * @param requiredType 想要获取的 {@code ServletResponse} 类型
     * @return 如果 {@code ServletResponse} 可以匹配指定的类型，那么返回该类型的 {@code
     * ServletResponse}，否则返回 {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNativeResponse(ServletResponse response, Class<T> requiredType) {
        if (requiredType != null) {
            if (requiredType.isInstance(response)) {
                return (T) response;
            } else if (response instanceof ServletResponseWrapper) {
                return getNativeResponse(((ServletResponseWrapper) response).getResponse(), requiredType);
            }
        }
        return null;
    }


    /**
     * 检索给定名称的第一个 Cookie ，注意这些 Cookie 可能在不同的路径或者域下
     *
     * @param request 当前 {@link HttpServletRequest} 请求
     * @param name    Cookie 名称
     * @return 返回指定名称的第一个 Cookie，如果没有找到则返回 {@code null}
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request, "Request must not be null");
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 确定给定请求的 Session ID（如果当前请求存在一个 Session ID 的话）
     *
     * @param request 当前 {@link HttpServletRequest} 请求
     * @return Session ID，如果没有找到则返回 {@code null}
     */
    public static String getSessionId(HttpServletRequest request) {
        Assert.notNull(request, "Request must not be null");
        HttpSession session = request.getSession(false);
        return (session != null ? session.getId() : null);
    }

    /**
     * 检查给定请求中指定名称的 Session 属性，如果该请求不存在 Session 或者 Session
     * 中不存在该属性则返回 {@code null}
     * 注意：如果给定请求没有 Session 是不会自动创建 Session 的。
     *
     * @param request 当前 {@link HttpServletRequest} 请求
     * @param name    Session 属性名称
     * @return 如果找到了则返回 Session 属性值，否则返回 {@code null}
     */
    public static Object getSessionAttribute(HttpServletRequest request, String name) {
        Assert.notNull(request, "Request must not be null");
        HttpSession session = request.getSession(false);
        return (session != null ? session.getAttribute(name) : null);
    }

    /**
     * 检查给定请求中指定名称的 Session 属性，如果该请求不存在 Session 或者 Session
     * 中不存在该属性则抛出 {@link IllegalStateException}
     * 注意：如果给定请求没有 Session 是不会自动创建 Session 的。
     *
     * @param request 当前 {@link HttpServletRequest} 请求
     * @param name    Session 属性名称
     * @return 如果找到了则返回 Session 属性值，否则抛出 {@link IllegalStateException}
     * @throws IllegalStateException 如果 Session 不存在或者 Session 属性不存在
     */
    public static Object getRequiredSessionAttribute(HttpServletRequest request, String name)
            throws IllegalStateException {
        Object attr = getSessionAttribute(request, name);
        if (attr == null) {
            throw new IllegalStateException("No session attribute '" + name + "' found");
        }
        return attr;
    }

    /**
     * 根据给定属性键值对设置 Session 属性，如果值为 {@code null} 并且 Session 存在，那么
     * 将执行 {@link HttpSession#removeAttribute(String)} 操作。
     * 注意：如果没有必要是不会自动创建 Session 的
     *
     * @param request 当前 {@link HttpServletRequest} 请求
     * @param name    Session 属性名
     * @param value   Session 属性值
     */
    public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
        Assert.notNull(request, "Request must not be null");
        if (value != null) {
            // 执行设置属性时，如果 Session 不存在则创建 Session
            request.getSession().setAttribute(name, value);
        } else {
            // 执行移除属性时，如果 Session 不存在则不会创建 Session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(name);
            }
        }
    }

    /**
     * 返回由指定 ServletContext 确定的 Spring Web 应用的临时目录
     *
     * @param servletContext 指定 Web 应用的 {@link ServletContext}
     * @return 临时目录文件对象
     */
    public static File getTempDir(ServletContext servletContext) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        return (File) servletContext.getAttribute(TEMP_DIR_CONTEXT_ATTRIBUTE);
    }

    public static Path getTempDir() {
        return Paths.get(getTempDir(currentServletContext()).toURI());
    }

    /**
     * 基于 {@code Origin}，{@code Host}，{@code Forwarded} 和 {@code X-Forwarded-Host} 这些头部信息
     * 来确定请求是否是同源的。
     *
     * @return 如果请求是同源的话则返回 {@code true}，反之跨域则返回 {@code false}
     */
    public static boolean isSameOrigin(HttpRequest request) {
        return org.springframework.web.util.WebUtils.isSameOrigin(request);
    }

    /**
     * 检查给定请求的来源是否在允许的范围内，如果合法源列表包含了 * 这个通配符则表示所有的源都是允许的；
     * 一个空的合法源列表表示只允许同源请求
     *
     * @return 如果请求来源是允许的，那么返回 {@code true}，否则返回 {@code false}
     * @see <a href="https://tools.ietf.org/html/rfc6454">RFC 6454: The Web Origin Concept</a>
     */
    public static boolean isValidOrigin(HttpRequest request, Collection<String> allowedOrigins) {
        Assert.notNull(request, "Request must not be null");
        Assert.notNull(allowedOrigins, "Allowed origins must not be null");

        String origin = request.getHeaders().getOrigin();
        if (origin == null || allowedOrigins.contains("*")) {
            return true;
        } else if (CollectionUtils.isEmpty(allowedOrigins)) {
            return isSameOrigin(request);
        } else {
            return allowedOrigins.contains(origin);
        }
    }

    /**
     * 解析给定矩形变量字符串。
     * 矩形变量字符串实例：{@code "q1=a;q1=b;q2=a,b,c"}，解析完毕之后的 {@link MultiValueMap}
     * 将包含以下键值对：
     * <pre>
     *     "q1": [
     *         "a", "b"
     *     ], "q2": [
     *         "a", "b", "c"
     *     ]
     * </pre>
     *
     * @param matrixVariables 未解析的矩形变量字符串
     * @return 一个多值变量的 Map，其中 Key 为变量名，Value 为 变量值列表（注意：返回的 Map 永远非 {@code null}）
     */
    public static MultiValueMap<String, String> parseMatrixVariables(String matrixVariables) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        if (!StringUtils.hasText(matrixVariables)) {
            return result;
        }
        StringTokenizer pairs = new StringTokenizer(matrixVariables, ";");
        while (pairs.hasMoreTokens()) {
            String pair = pairs.nextToken();
            int index = pair.indexOf('=');
            if (index != -1) {
                String name = pair.substring(0, index);
                String rawValue = pair.substring(index + 1);
                for (String value : StringUtils.commaDelimitedListToStringArray(rawValue)) {
                    result.add(name, value);
                }
            } else {
                result.add(pair, "");
            }
        }
        return result;
    }

}
