package com.jerryjin.kit.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019/5/8 12:51
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks, ApplicationState {

    private static final String TAG = "ActivityLifecycleCb";
    private List<ActivityWrapper> activityWrappers = new ArrayList<>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityWrappers.add(new ActivityWrapper(activity, savedInstanceState, ActivityState.Config.STATE_CREATED));
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is created.");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        checkActivity(activity, ActivityState.Config.STATE_STARTED);
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is started.");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        checkActivity(activity, ActivityState.Config.STATE_RESUMED);
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is resumed.");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        checkActivity(activity, ActivityState.Config.STATE_PAUSED);
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is paused.");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        checkActivity(activity, ActivityState.Config.STATE_STOPPED);
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is stopped.");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        checkActivity(activity, outState);
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + "'s state is saved.");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        checkActivity(activity, ActivityState.Config.STATE_DESTROYED);
        activityWrappers.remove(findActivityWrapper(activity));
        Log.i(TAG, "Activity " + activity.getClass().getSimpleName() + " is destroyed.");
    }

    public List<ActivityWrapper> getActivityWrappers() {
        return activityWrappers;
    }

    private void checkActivity(Activity activity, int activityState) {
        ActivityWrapper activityWrapper = findActivityWrapper(activity);
        if (activityWrapper == null) {
            activityWrapper = new ActivityWrapper(activity, activityState);
            activityWrappers.add(activityWrapper);
        } else {
            activityWrapper.setActivityState(activityState);
        }
    }

    private void checkActivity(Activity activity, Bundle outState) {
        ActivityWrapper activityWrapper = findActivityWrapper(activity);
        if (activityWrapper == null) {
            activityWrapper = new ActivityWrapper(activity, outState, ActivityState.Config.STATE_SAVE_INSTANCE_STATE);
            activityWrappers.add(activityWrapper);
        } else {
            activityWrapper.setActivityInstanceState(outState);
            activityWrapper.setActivityState(ActivityState.Config.STATE_SAVE_INSTANCE_STATE);
        }
    }

    /**
     * Locate a specific ActivityWrapper object.
     * <p/>
     * Although the structure of the activityWrappers is an ArrayList. Every time an Activity go through each phase of its lifecycle,
     * the data will be updated accordingly. including adding and removing. Thus, there can only be single instance of each started Activity.
     *
     * @param activity The specific activity in which ActivityWrapper to be found.
     * @return The specific ActivityWrapper object.
     */
    private ActivityWrapper findActivityWrapper(Activity activity) {
        ActivityWrapper wrapper = null;
        for (ActivityWrapper activityWrapper : activityWrappers) {
            Activity tmp = activityWrapper.getActivity();
            if (tmp == activity) {
                wrapper = activityWrapper;
                break;
            }
        }
        return wrapper;
    }

    public Date getActivityAddedAt(Activity activity) {
        ActivityWrapper wrapper = findActivityWrapper(activity);
        return new Date(wrapper.getAddedAt());
    }

    @Override
    public int getItemCount() {
        return activityWrappers.size();
    }

    @Override
    public boolean isVisibleToUser(Activity activity) {
        ActivityWrapper activityWrapper = findActivityWrapper(activity);
        if (activityWrapper != null) {
            int state = activityWrapper.getActivityState().getState();
            return state != ActivityState.Config.STATE_PAUSED
                    && state != ActivityState.Config.STATE_STOPPED
                    && state != ActivityState.Config.STATE_DESTROYED;
        }
        return false;
    }

    @Override
    public Activity getCurrent() {
        if (activityWrappers.size() != 0) {
            ActivityWrapper current = activityWrappers.get(0);
            for (ActivityWrapper activityWrapper : activityWrappers) {
                if (current.getAddedAt() < activityWrapper.getAddedAt()) {
                    current = activityWrapper;
                }
            }
            return current.getActivity();
        }
        return null;
    }
}
