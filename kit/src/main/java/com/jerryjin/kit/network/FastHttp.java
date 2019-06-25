package com.jerryjin.kit.network;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Jerry
 * Generated at: 2019-06-22 00:04
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
public class FastHttp {

    private OkHttpClient client;
    private FastHttpOptions httpOptions;

    private FastHttp() {
    }

    public static FastHttp getInstance() {
        return Holder.instance;
    }

    private void ensureHttpOptions() {
        if (httpOptions == null) {
            httpOptions = new FastHttpOptions();
        }
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    private Request buildRequest() {
        Headers headers = httpOptions.getHeaders();
        boolean hasHeaders = headers != null;
        Request.Builder builder = new Request.Builder()
                .url(httpOptions.getUrlStr());
        if (hasHeaders) {
            builder.headers(headers);
        }
        return builder.build();
    }

    public FastHttp setHttpOptions(FastHttpOptions fastHttpOptions) {
        this.httpOptions = fastHttpOptions;
        return Holder.instance;
    }

    public FastHttp setUrl(String url) {
        ensureHttpOptions();
        this.httpOptions.setUrlStr(url);
        return Holder.instance;
    }

    public void runAsync(CallbackImpl callback) {
        client.newCall(buildRequest())
                .enqueue(callback);
    }

    public Response runSync() {
        try {
            return client.newCall(buildRequest()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class Holder {
        private static final FastHttp instance = new FastHttp();
    }
}
