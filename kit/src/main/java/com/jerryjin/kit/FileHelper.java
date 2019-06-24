package com.ci123.babyweekend.imgs.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 17:51
 * WeChat: enGrave93
 * Description:
 */
public class FileHelper {
    private static final String TAG = "FileHelper";
    private static final boolean DEBUG = false;

    public static <T extends Writable> void saveFile(Context context, String name, T tFile) {
        if (context == null || TextUtils.isEmpty(name) || tFile == null) {
            Log.e(TAG, "saveFile :: Null given parameters.");
            return;
        }
        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tFile);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Writable> T readFile(Context context, String name) {
        if (context == null || TextUtils.isEmpty(name)) {
            Log.e(TAG, "readFile :: Null given parameters.");
            return null;
        }
        try {
            FileInputStream fis = context.openFileInput(name);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            T file = (T) ois.readObject();
            fis.close();
            bis.close();
            ois.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBitmap(Context context, String name, Bitmap bitmap) {
        if (context == null || TextUtils.isEmpty(name) || bitmap == null) {
            return;
        }
        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readBitmap(Context context, String name) {
        if (context == null || TextUtils.isEmpty(name)) {
            return null;
        }
        try {
            FileInputStream fis = context.openFileInput(name);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
