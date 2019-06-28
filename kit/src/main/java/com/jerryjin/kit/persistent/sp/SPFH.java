package com.jerryjin.kit.persistent.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.collection.ArraySet;

import com.jerryjin.kit.network.pojo.KVPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Jerry
 * Generated at: 2019-06-24 23:28
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 * <p>
 * SharedPreferencesFastHelper is it's whole name.
 * </p>
 * <p>
 * Alothough I have optimized the PUT efficiency for {@link SharedPreferences}, if you want to store big object, I strongly recommend
 * using {@link com.jerryjin.kit.persistent.mmkv.MMKVHelper} instead.
 * </p>
 */
@SuppressWarnings("SpellCheckingInspection")
public class SPFH {

    private static final String TAG = "SPFH";
    private static final boolean DEBUG = false;

    private static final String FILE_NAME = "SPFH_FILE";
    private static final String SEPARATOR = ",";
    private static final String EMPTY_STRING = "";
    private SharedPreferences sharedPreferences;
    private List<SPRequestOp> args = new ArrayList<>();

    private SPFH(Context context, String fileName) {
        String spFileName;
        if (!TextUtils.isEmpty(fileName)) {
            spFileName = fileName;
        } else {
            spFileName = FILE_NAME;
        }
        sharedPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }

    public static SPFH with(Context context) {
        return new SPFH(context, null);
    }

    public static SPFH with(Context context, String fileName) {
        return new SPFH(context, fileName);
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return sharedPreferences.getBoolean(name, defaultValue);
    }

    public SPFH putBoolean(String name, boolean value) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(name, value), SPRequestOp.PUT_BOOLEAN);
        args.add(op);
        return this;
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public SPFH putInt(String key, int value) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, value), SPRequestOp.PUT_INT);
        args.add(op);
        return this;
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public SPFH putFloat(String key, float value) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, value), SPRequestOp.PUT_FLOAT);
        args.add(op);
        return this;
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public SPFH putLong(String key, long value) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, value), SPRequestOp.PUT_LONG);
        args.add(op);
        return this;
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public SPFH putString(String key, String value) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, value), SPRequestOp.PUT_String);
        args.add(op);
        return this;
    }

    public Set<String> getStringSet(String key, Set<String> defaultValues) {
        return sharedPreferences.getStringSet(key, defaultValues);
    }

    public SPFH putStringSet(String key, Set<String> values) {
        StringBuilder builder = new StringBuilder();
        for (String s : values) {
            builder
                    .append(s)
                    .append(SEPARATOR);
        }
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, builder.toString()), SPRequestOp.PUT_STRING_SET);
        args.add(op);
        return this;
    }

    public SPFH remove(String key) {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(key, EMPTY_STRING), SPRequestOp.REMOVE);
        args.add(op);
        return this;
    }

    public SPFH clear() {
        SPRequestOp op = new SPRequestOp(new KVPair<String, Object>(EMPTY_STRING, EMPTY_STRING), SPRequestOp.REMOVE);
        args.add(op);
        return this;
    }

    public void apply() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (SPRequestOp op : args) {
            KVPair<String, Object> kvPair = op.getKvPair();
            String key = kvPair.getKey();
            Object value = kvPair.getValue();
            int opMode = op.getOp();
            if (opMode == SPRequestOp.PUT_BOOLEAN) {
                editor.putBoolean(key, (Boolean) value);
            } else if (opMode == SPRequestOp.PUT_INT) {
                editor.putInt(key, (Integer) value);
            } else if (opMode == SPRequestOp.PUT_FLOAT) {
                editor.putFloat(key, (Float) value);
            } else if (opMode == SPRequestOp.PUT_LONG) {
                editor.putLong(key, (Long) value);
            } else if (opMode == SPRequestOp.PUT_String) {
                editor.putString(key, (String) value);
            } else if (opMode == SPRequestOp.PUT_STRING_SET) {
                String[] strings = ((String) value).split(SEPARATOR);
                Set<String> stringSet = new ArraySet<>();
                Collections.addAll(stringSet, strings);
                editor.putStringSet(key, stringSet);
            } else if (opMode == SPRequestOp.REMOVE) {
                editor.remove(key);
            } else if (opMode == SPRequestOp.CLEAR) {
                editor.clear();
            }
        }
        editor.apply();
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public void registerOnSharedPreferencesChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferencesChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}
