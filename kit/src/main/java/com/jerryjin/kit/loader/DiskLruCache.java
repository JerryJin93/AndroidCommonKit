package com.jerryjin.kit.loader;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 17:13
 * WeChat: enGrave93
 * Description:
 */
public class DiskLruCache {
    private static final String TAG = "DiskLruCache";
    private static final boolean DEBUG = false;

    private File directory;
    private WeakReference<Context> mContextRef;
    private LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>();

    public Worker put(String key, Bitmap bitmap) {
        // FileHelper.saveBitmap(mContextRef.get(), key, bitmap);
        return null;
    }

    @SuppressWarnings("WeakerAccess")
    public static final class Worker {

        private Entry entry;
        private boolean committed;
    }

    @SuppressWarnings("WeakerAccess")
    public class Entry {
        private File dir;
    }
}
