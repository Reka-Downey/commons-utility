package me.junbin.commons.http;

import me.junbin.commons.util.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/24 20:39
 * @description :
 */
public class HttpProxy {

    private final DefaultProxyRoutePlanner proxy;
    private final CredentialsProvider credential;

    public HttpProxy(DefaultProxyRoutePlanner proxy, CredentialsProvider credential) {
        this.proxy = proxy;
        this.credential = credential;
    }

    public static Builder custom() {
        return new Builder();
    }

    public DefaultProxyRoutePlanner getProxy() {
        return proxy;
    }

    public CredentialsProvider getCredential() {
        return credential;
    }

    public static class Builder {

        private String host = null;
        private int port = -1;
        private String scheme = "http";
        private String username = null;
        private String password = null;

        private Builder() {
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder credential(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public HttpProxy build() {
            if (StringUtils.isEmpty(host)) {
                throw new IllegalArgumentException("host must not null");
            }
            if (StringUtils.isEmpty(scheme)) {
                throw new IllegalArgumentException("scheme must not null");
            }

            HttpHost httpHost = new HttpHost(host, port, scheme);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(httpHost);

            if (StringUtils.notNull(username) && StringUtils.notNull(password)) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(
                        new AuthScope(host, port),
                        new UsernamePasswordCredentials(username, password)
                );
                return new HttpProxy(routePlanner, credentialsProvider);
            }

            return new HttpProxy(routePlanner, null);
        }

    }

}
