package com.jerryjin.kit.network.pojo;

import android.util.ArrayMap;

import com.jerryjin.kit.data.MapHelper;

import java.util.List;
import java.util.Map;

import okhttp3.Headers;

/**
 * Author: Jerry
 * Generated at: 2019-06-22 00:30
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
public class HeaderParams {

    private Map<String, String> headers = new ArrayMap<>();

    public HeaderParams() {
    }

    public HeaderParams add(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HeaderParams add(KVPair<String, String> kvPair) {
        headers.put(kvPair.getKey(), kvPair.getValue());
        return this;
    }

    private HeaderParams addAll(List<KVPair<String, String>> kvPairs) {
        MapHelper.addAll(headers, kvPairs);
        return this;
    }

    public Headers build() {
        return Headers.of(headers);
    }
}
