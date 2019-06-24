package com.jerryjin.kit.network;

import android.util.Log;

import com.jerryjin.kit.network.pojo.KVPair;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Jerry
 * Generated at: 2019-06-22 00:43
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class MapHelper {

    private static final String TAG = "MapHelper";


    private MapHelper() {
    }

    public static <K, V> void add(Map<K, V> map, KVPair<K, V> bean) {
        if (map != null) {
            map.put(bean.getKey(), bean.getValue());
        }
    }

    public static <K, V> void addAll(Map<K, V> map, List<KVPair<K, V>> beans) {
        if (beans == null || beans.isEmpty()) {
            Log.e(TAG, "Null list.");
            return;
        }
        for (KVPair<K, V> pair : beans) {
            add(map, pair);
        }
    }

    public static <K, V> void addAll(Map<K, V> map, Set<KVPair<K, V>> beans) {
        if (beans == null || beans.isEmpty()) {
            return;
        }
        for (KVPair<K, V> pair : beans) {
            add(map, pair);
        }
    }

    @SafeVarargs
    public static <K, V> void addAll(Map<K, V> map, KVPair<K, V>... beans) {
        if (beans == null || beans.length == 0) {
            return;
        }
        for (KVPair<K, V> bean : beans) {
            add(map, bean);
        }
    }

}
