package com.jerryjin.kit.network;

import com.jerryjin.kit.network.pojo.HeaderParams;

import java.net.URL;

import okhttp3.Headers;

/**
 * Author: Jerry
 * Generated at: 2019-06-22 00:22
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class FastHttpOptions {

    private String url;
    private Headers headers;
    private HeaderParams headerParams = new HeaderParams();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public FastHttpOptions addHeader(String name, String value) {
        headerParams.add(name, value);
        return this;
    }
}
