package com.jerryjin.kit.persistent.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * Author: Jerry
 * Generated at: 2019-06-25 15:24
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: A helper for {@link MMKV}.
 */
public class MMKVHelper {

    private MMKV mmkv;

    private MMKVHelper() {
        mmkv = MMKV.defaultMMKV();
    }

    public static MMKVHelper with(Context context) {
        MMKV.initialize(context);
        return new MMKVHelper();
    }

    public static MMKVHelper with(String rootDir) {
        MMKV.initialize(rootDir);
        return new MMKVHelper();
    }

    public static String getRootDir() {
        return MMKV.getRootDir();
    }

    public Editor edit() {
        return new Editor();
    }

    public byte[] getBytes(String key) {
        return mmkv.decodeBytes(key);
    }

    public boolean getBool(String key) {
        return mmkv.decodeBool(key);
    }

    public boolean getBool(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

    public int getInt(String key) {
        return mmkv.decodeInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }

    public float getFloat(String key) {
        return mmkv.decodeFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
        return mmkv.decodeFloat(key, defaultValue);
    }

    public double getDouble(String key) {
        return mmkv.decodeDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return mmkv.decodeDouble(key, defaultValue);
    }

    public long getLong(String key) {
        return mmkv.decodeLong(key);
    }

    public long getLond(String key, long defaultValue) {
        return mmkv.decodeLong(key, defaultValue);
    }

    public String getString(String key) {
        return mmkv.decodeString(key);
    }

    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    public Set<String> getStringSet(String key) {
        return mmkv.decodeStringSet(key);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return mmkv.decodeStringSet(key, defaultValue);
    }

    public <T extends Parcelable> Parcelable getParcelable(String key, Class<T> tClass) {
        return mmkv.decodeParcelable(key, tClass);
    }

    public <T extends Parcelable> Parcelable getParcelable(String key, Class<T> tClass, T defaultValue) {
        return mmkv.decodeParcelable(key, tClass, defaultValue);
    }

    public boolean containsKey(String key) {
        return mmkv.containsKey(key);
    }

    public String[] getAllKeys() {
        return mmkv.allKeys();
    }

    public int getValueSize(String key) {
        return mmkv.getValueSize(key);
    }

    public int getValueActualSize(String key) {
        return mmkv.getValueActualSize(key);
    }

    public void importFromSharedPreferences(String myId, SharedPreferences sharedPreferences) {
        MMKV mmkv = MMKV.mmkvWithID(myId);
        mmkv.importFromSharedPreferences(sharedPreferences);
    }

    @SuppressWarnings("UnusedReturnValue")
    public class Editor {

        public Editor putBytes(String key, byte[] value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putBool(String key, boolean value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putInt(String key, int value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putFloat(String key, float value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putDouble(String key, double value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putLong(String key, long value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putString(String key, String value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putStringSet(String key, Set<String> value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor putParcelable(String key, Parcelable value) {
            mmkv.encode(key, value);
            return this;
        }

        public Editor removeValueForKey(String key) {
            mmkv.removeValueForKey(key);
            return this;
        }

        public Editor removeValuesForKeys(String[] keys) {
            mmkv.removeValuesForKeys(keys);
            return this;
        }

        public Editor clearAll() {
            mmkv.clearAll();
            return this;
        }
    }
}
