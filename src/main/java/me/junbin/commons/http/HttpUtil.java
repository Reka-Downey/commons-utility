package me.junbin.commons.http;

import me.junbin.commons.charset.Charsets;
import me.junbin.commons.util.Args;
import org.apache.http.*;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2016/11/7 20:27
 * @description :
 */
public class HttpUtil {

    private static final int TIMEOUT = 10 * 60 * 1000;

    private static RequestConfig defaultConfig = RequestConfig.custom()
                                                              .setSocketTimeout(TIMEOUT)
                                                              .setConnectTimeout(TIMEOUT)
                                                              .setConnectionRequestTimeout(TIMEOUT)
                                                              .build();

    private static HttpProxy shadowsocksProxy = HttpProxy.custom()
                                                         .host("127.0.0.1")
                                                         .port(1080)
                                                         .scheme("http")
                                                         .build();

    private static CloseableHttpClient httpClient = HttpClients.custom()
                                                               .setDefaultRequestConfig(defaultConfig)
                                                               .build();

    private static CloseableHttpClient proxyClient = HttpClientBuilder.create()
                                                                      .setDefaultRequestConfig(RequestConfig.DEFAULT)
                                                                      .setRoutePlanner(shadowsocksProxy.getProxy())
                                                                      .build();

    private static final Header JSON = new BasicHeader("Content-Type", "application/json; charset=UTF-8");
    private static final Charset DEFAULT_CHARSET = Charsets.UTF8;

    public static RequestConfig getDefaultConfig() {
        return defaultConfig;
    }

    public static HttpProxy getShadowsocksProxy() {
        return shadowsocksProxy;
    }

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static CloseableHttpClient getProxyClient() {
        return proxyClient;
    }

    public static void globalRequestConfig(final RequestConfig requestConfig) {
        httpClient = HttpClients.custom()
                                .setDefaultRequestConfig(requestConfig)
                                .build();
    }

    public static void globalProxy(final HttpProxy httpProxy) {
        globalSetting(httpProxy, RequestConfig.DEFAULT);
    }

    public static void globalSetting(final HttpProxy httpProxy, final RequestConfig requestConfig) {
        HttpProxy proxy = Args.notNull(httpProxy);
        DefaultProxyRoutePlanner router = proxy.getProxy();
        router = Args.notNull(router);
        CredentialsProvider credential = proxy.getCredential();
        if (credential != null) {
            proxyClient = HttpClients.custom()
                                     .setDefaultRequestConfig(requestConfig)
                                     .setDefaultCredentialsProvider(credential)
                                     .setRoutePlanner(router)
                                     .build();
        } else {
            proxyClient = HttpClients.custom()
                                     .setDefaultRequestConfig(requestConfig)
                                     .setRoutePlanner(router)
                                     .build();
        }
    }


    public static String doGetWithGzip(final String url) throws IOException {
        return doGetWithGzip(url, false);
    }

    public static String proxyGetWIthGzip(final String url) throws IOException {
        return doGetWithGzip(url, true);
    }

    public static String doGetWithGzip(final String url, final boolean usingProxy) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Encoding", "gzip");
        if (usingProxy) {
            return execute(proxyClient, httpGet);
        } else {
            return execute(httpClient, httpGet);
        }
    }

    /**
     * 　　执行 GET 请求并获取响应体的内容，如果 {@code url} 携带查询参数，那么要求这些查询参数都
     * 已经（使用 {@link java.net.URLDecoder#decode(String, String)}）编码过了
     *
     * @param url 请求 url
     * @return 响应体
     */
    public static String doGet(final String url) throws IOException {
        return doGet(url, false);
    }

    public static String proxyGet(final String url) throws IOException {
        return doGet(url, true);
    }

    public static String doGet(final String url, final boolean usingProxy) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        if (usingProxy) {
            return execute(proxyClient, httpGet);
        } else {
            return execute(httpClient, httpGet);
        }
    }

    public static String doGet(String url, Header... headers) throws IOException {
        return doGet(url, false, headers);
    }

    public static String proxyGet(String url, Header... headers) throws IOException {
        return doGet(url, true, headers);
    }

    public static String doGet(String url, boolean usingProxy, Header... headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        for (Header header : headers) {
            httpGet.addHeader(header);
        }
        if (usingProxy) {
            return execute(proxyClient, httpGet);
        } else {
            return execute(httpClient, httpGet);
        }
    }

    /**
     * 　　对请求查询参数列表进行 URL 编码，之后执行 GET 请求并获取响应体的内容
     *
     * @param url       请求 url
     * @param paramList 请求的查询参数列表，不需要手动进行编码
     * @return 响应体
     */
    public static String doGet(String url, List<NameValuePair> paramList) throws IOException {
        return doGet(url, paramList, false);
    }

    public static String proxyGet(String url, List<NameValuePair> paramList) throws IOException {
        return doGet(url, paramList, true);
    }

    public static String doGet(String url, List<NameValuePair> paramList, boolean usingProxy) throws IOException {
        return doGet(url, paramList, DEFAULT_CHARSET, usingProxy);
    }

    public static String doGet(String url, List<NameValuePair> paramList, Charset encodedCharset, boolean usingProxy) throws IOException {
        String params = URLEncodedUtils.format(paramList, encodedCharset);
        //String params = EntityUtils.toString(new UrlEncodedFormEntity(paramList, encodedCharset));
        if (usingProxy) {
            return proxyGet(url + '?' + params);
        } else {
            return doGet(url + '?' + params);
        }
    }


    /**
     * 　　执行表单 POST 请求并获取响应体的内容
     *
     * @param url       请求 url
     * @param paramList 请求的表单参数列表
     * @return 响应体
     */
    public static String doFormPost(String url, List<NameValuePair> paramList) throws IOException {
        return doFormPost(url, paramList, false);
    }

    public static String proxyFormPost(String url, List<NameValuePair> paramList) throws IOException {
        return doFormPost(url, paramList, true);
    }

    public static String doFormPost(String url, List<NameValuePair> paramList, boolean usingProxy) throws IOException {
        return doFormPost(url, paramList, DEFAULT_CHARSET, usingProxy);
    }

    public static String doFormPost(String url, List<NameValuePair> paramList, Charset encodedCharset, boolean usingProxy) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList, encodedCharset));
        if (usingProxy) {
            return execute(proxyClient, httpPost);
        } else {
            return execute(httpClient, httpPost);
        }
    }

    /**
     * 　　该方法与 {@link #doFormPost(String, List)} 完全一致，只是方法名称略有不同
     *
     * @param url       请求 url
     * @param paramList 请求的表单参数列表
     * @return 响应体
     * @see #doFormPost(String, List)
     */
    public static String doPost(String url, List<NameValuePair> paramList) throws IOException {
        return doFormPost(url, paramList);
    }

    public static String proxyPost(String url, List<NameValuePair> paramList) throws IOException {
        return proxyFormPost(url, paramList);
    }

    /**
     * 　　执行 RESTful 风格的 POST 请求并获取响应体的内容
     *
     * @param url      请求 url
     * @param jsonBody json 字符串参数
     * @return 响应体
     */
    public static String doPost(String url, String jsonBody) throws IOException {
        return doPost(url, jsonBody, false);
    }

    public static String proxyPost(String url, String jsonBody) throws IOException {
        return doPost(url, jsonBody, true);
    }

    public static String doPost(String url, String jsonBody, boolean usingProxy) throws IOException {
        return doPost(url, jsonBody, DEFAULT_CHARSET, usingProxy);
    }

    public static String doPost(String url, String jsonBody, Charset encodedCharset, boolean usingProxy) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (encodedCharset == DEFAULT_CHARSET) {
            httpPost.addHeader(JSON);
        } else {
            httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=" + encodedCharset.name()));
        }
        httpPost.setEntity(new StringEntity(jsonBody, encodedCharset));
        if (usingProxy) {
            return execute(proxyClient, httpPost);
        } else {
            return execute(httpClient, httpPost);
        }
    }

    /**
     * 　　执行不携带参数的 POST 请求并返回响应体的内容
     *
     * @param url 请求 url
     * @return 响应体
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, false);
    }

    public static String proxyPost(String url) throws IOException {
        return doPost(url, true);
    }

    public static String doPost(String url, boolean usingProxy) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (usingProxy) {
            return execute(proxyClient, httpPost);
        } else {
            return execute(httpClient, httpPost);
        }
    }

    public static String execute(final CloseableHttpClient client, final HttpUriRequest request) throws IOException {
        Args.notNull(client);
        String responseBody;
        try (CloseableHttpResponse response = client.execute(request)) {
            responseBody = responseBody(response);
        }
        return responseBody;
    }

    /**
     * 　　判断响应是否正常，响应状态码大于等于 200 并且小于 300 即正常。
     *
     * @param response 响应
     * @return 响应正常返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isReplyOk(HttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    /**
     * 　　解析响应并提取其中响应体的内容，之后将响应体消费（关闭）掉。
     *
     * @param response 响应
     * @return 响应体
     */
    public static String responseBody(HttpResponse response) throws IOException {
        return responseBody(response, DEFAULT_CHARSET);
    }

    /**
     * 　　解析响应并提取其中响应体的内容，之后将响应体消费（关闭）掉。
     *
     * @param response 响应
     * @return 响应体
     */
    public static String responseBody(HttpResponse response, Charset decodedCharset) throws IOException {
        String responseBody;
        if (!isReplyOk(response)) {
            StatusLine statusLine = response.getStatusLine();
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        HttpEntity responseEntity = response.getEntity();
        //responseEntity = new GzipDecompressingEntity(responseEntity);
        responseBody = EntityUtils.toString(responseEntity, decodedCharset);
        EntityUtils.consume(responseEntity);
        return responseBody;
    }

    /**
     * 关闭 {@link #httpClient}
     */
    public static void closeHttpClient() throws IOException {
        httpClient.close();
    }

    /**
     * 关闭 {@link #httpClient}，并对可能发生的异常置之不理。
     */
    public static void closeHttpClientQuiet() {
        try {
            httpClient.close();
        } catch (IOException ignore) {
        }
    }

    /**
     * 关闭 {@link #httpClient}
     */
    public static void closeProxyClient() throws IOException {
        proxyClient.close();
    }

    /**
     * 关闭 {@link #httpClient}，并对可能发生的异常置之不理。
     */
    public static void closeProxyClientQuiet() {
        try {
            proxyClient.close();
        } catch (IOException ignore) {
        }
    }

}