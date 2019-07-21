package com.jerryjin.kit.lifecycle;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * Author: Jerry
 * Generated at: 2019/5/8 14:59
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ActivityWrapper {

    private Activity mActivity;
    private Bundle mActivityInstanceState;
    private ActivityState mActivityState;
    private long mAddedAt;
    public Object extra;

    public ActivityWrapper(Activity activity, Bundle activityInstanceState, ActivityState activityState) {
        this.mActivity = activity;
        this.mActivityInstanceState = activityInstanceState;
        this.mActivityState = activityState;
        generateAddedAt();
    }

    public ActivityWrapper(Activity activity, Bundle activityInstanceState, int activityState) {
        this(activity, activityInstanceState, new ActivityState(activityState));
    }

    public ActivityWrapper(Activity activity, int activityState) {
        this(activity, null, activityState);
    }

    private void generateAddedAt() {
        mAddedAt = System.currentTimeMillis();
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public Bundle getActivityInstanceState() {
        return mActivityInstanceState;
    }

    public void setActivityInstanceState(Bundle mActivityInstanceState) {
        this.mActivityInstanceState = mActivityInstanceState;
    }

    public ActivityState getActivityState() {
        return mActivityState;
    }

    public void setActivityState(ActivityState mActivityState) {
        this.mActivityState = mActivityState;
    }

    public void setActivityState(int mActivityState) {
        this.mActivityState = new ActivityState(mActivityState);
    }

    public long getAddedAt() {
        return mAddedAt;
    }

    public void setAddedAt(long mAddedAt) {
        this.mAddedAt = mAddedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActivityWrapper{" +
                "mActivity=" + mActivity +
                ", mActivityInstanceState=" + mActivityInstanceState +
                ", mActivityState=" + mActivityState +
                ", mAddedAt=" + mAddedAt +
                ", extra=" + extra +
                '}';
    }
}
