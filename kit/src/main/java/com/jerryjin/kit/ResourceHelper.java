package com.ci123.babyweekend.imgs;

import android.content.Context;
import android.util.Log;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 12:57
 * WeChat: enGrave93
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ResourceHelper {

    private static final String TAG = "ResourceHelper";
    private static final boolean DEBUG = false;
    /**
     * 0 is not a valid resource ID.
     */
    private static final int ERROR_CODE = 0;

    /**
     * Retrieve the ID of the specified id resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the id resource.
     * @return The ID that refers to the id resource.
     */
    public static int getId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "id");
    }

    /**
     * Retrieve the ID of the specified layout resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the layout resource.
     * @return The ID that represents the layout resource.
     */
    public static int getLayoutId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "layout");
    }

    /**
     * Retrieve the ID of the specified drawable resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the drawable resource.
     * @return The ID that represents the drawable resource.
     */
    public static int getDrawableId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "drawable");
    }

    /**
     * Retrieve the ID of the specified mipmap resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the mipmap resource.
     * @return The ID that represents the mipmap resource.
     */
    public static int getMipmapId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "mipmap");
    }

    /**
     * Retrieve the ID of the specified color resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the color resource you want to retrieve.
     * @return The ID that represents the color resource you want to retrieve.
     */
    public static int getColorId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "color");
    }

    /**
     * Retrieve the ID of the specified dimen resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the dimen resource you want to retrieve.
     * @return The ID that represents the dimen resource you want to retrieve.
     */
    public static int getDimenId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "dimen");
    }

    /**
     * Retrieve the ID of the specified attr resource.
     *
     * @param context      The given context.
     * @param resourceName The name of the attr resource you want to retrieve.
     * @return The ID that represents the attr resource you want to retrieve.
     */
    public static int getAttrId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "attr");
    }

    /**
     * Retrieve the ID of the specified style resource you need.
     *
     * @param context      The given context.
     * @param resourceName The name of the style resource you want to get.
     * @return The ID that refers to the style resource you want to retrieve.
     */
    public static int getStyleId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "style");
    }

    /**
     * Retrieve the ID of the specified anim resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the anim resource you need.
     * @return The ID that represents the anim resource you need.
     */
    public static int getAnimId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "anim");
    }

    /**
     * Retrieve the ID of the specified array resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the array resource you want to get.
     * @return The ID that refers to the array resource you want to retrieve.
     */
    public static int getArrayId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "array");
    }

    /**
     * Retrieve the ID of the specified bool resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the bool resource you want.
     * @return The ID that refers to the bool resource you need.
     */
    public static int getBoolId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "bool");
    }

    /**
     * Retrieve the ID of the specified integer resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the integer resource you want.
     * @return The ID that refers to the integer resource you want.
     */
    public static int getIntegerId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "integer");
    }

    /**
     * Retrieve the ID of the specified string resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the string resource you need.
     * @return The ID that represents the string resource you want.
     */
    public static int getStringId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "string");
    }

    /**
     * Retrieve the ID of the specified resource you want.
     *
     * @param context      The given context.
     * @param resourceName The name of the resource you need.
     * @param defType      The type of this specific resource you need.
     * @return The ID that represents the resource you want.
     */
    public static int getIdentifierByType(Context context, String resourceName, String defType) {
        if (context == null) {
            Log.e(TAG, "Null given context.");
            return ERROR_CODE;
        }
        return context.getResources().getIdentifier(resourceName, defType, context.getPackageName());
    }
}
