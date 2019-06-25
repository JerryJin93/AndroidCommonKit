package com.jerryjin.kit.loader.img;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 15:38
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class MemoryLruCache {

    private static final String TAG = "MemoryLruCache";
    private static final boolean DEBUG = false;
    private LruCache<String, Bitmap> mMemoryCache;

    private MemoryLruCache() {
        // TODO: 2019/6/24 Recalculating the maxSize of the pool. Reference: Glide.
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mMemoryCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if (oldValue != null && !oldValue.isRecycled()) {
                    oldValue.recycle();
                }
            }

            @SuppressLint("ObsoleteSdkInt")
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int size;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    size = value.getAllocationByteCount();
                    if (DEBUG) {
                        Log.d(TAG, "Case: >= API 19. The size of bitmap mapped by key - " + key + " is " + size);
                    }
                    return size;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    size = value.getByteCount();
                    if (DEBUG) {
                        Log.d(TAG, "Case: >= API 12 && < API 19. The size of bitmap mapped by key - " + key + " is " + size);
                    }
                    return size;
                } else {
                    size = value.getRowBytes() * value.getHeight();
                    if (DEBUG) {
                        Log.d(TAG, "Case: < API 12. The size of bitmap mapped by key - " + key + " is " + size);
                    }
                    return size;
                }
            }
        };
    }

    public static MemoryLruCache getInstance() {
        return Holder.instance;
    }

    public Bitmap getBitmap(String key) {
        return !TextUtils.isEmpty(key) ? mMemoryCache.get(key) : null;
    }

    public void putBitmap(String key, Bitmap bitmap) {
        mMemoryCache.put(key, bitmap);
    }

    @SuppressWarnings({"unchecked", "JavaReflectionMemberAccess"})
    public void cleanCache(String... urls) {
        if (urls == null || urls.length == 0) {
            Log.e(TAG, "None selected bitmap, skip.");
            return;
        }
        try {
            Class clazz = Class.forName("android.util.LruCache");
            Field field = clazz.getDeclaredField("map");
            field.setAccessible(true);
            LinkedHashMap<String, Bitmap> map = (LinkedHashMap<String, Bitmap>) field.get(mMemoryCache);
            if (map == null) {
                Log.e(TAG, "Null map, skip.");
                return;
            }
            Iterator<Map.Entry<String, Bitmap>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Bitmap> entry = iterator.next();
                Bitmap bitmap = entry.getValue();
                for (String url : urls) {
                    if (TextUtils.equals(url, entry.getKey())) {
                        if (bitmap != null && !bitmap.isRecycled()) {
                            bitmap.recycle();
                        }
                        iterator.remove();
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"unchecked", "JavaReflectionMemberAccess"})
    public void cleanCaches() {
        try {
            Class clazz = Class.forName("android.util.LruCache");
            Field field = clazz.getDeclaredField("map");
            field.setAccessible(true);
            LinkedHashMap<String, Bitmap> map = (LinkedHashMap<String, Bitmap>) field.get(mMemoryCache);
            if (map == null) {
                Log.e(TAG, "Null map, skip.");
                return;
            }
            Iterator<Map.Entry<String, Bitmap>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Bitmap> entry = iterator.next();
                Bitmap bitmap = entry.getValue();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                iterator.remove();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static final MemoryLruCache instance = new MemoryLruCache();
    }

}
