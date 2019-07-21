package com.jerryjin.kit.lifecycle;

import androidx.annotation.NonNull;

/**
 * Author: Jerry
 * Generated at: 2019/5/8 14:58
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ActivityState {
    private int mState;

    public ActivityState(int mState) {
        this.mState = mState;
    }

    public int getState() {
        return mState;
    }

    public void setState(int mState) {
        this.mState = mState;
    }

    private String parseState(int state) {
        String stateStr = "";
        switch (state) {
            case Config.STATE_CREATED:
                stateStr = "created";
                break;
            case Config.STATE_STARTED:
                stateStr = "started";
                break;
            case Config.STATE_RESUMED:
                stateStr = "resumed";
                break;
            case Config.STATE_SAVE_INSTANCE_STATE:
                stateStr = "saved instance state";
                break;
            case Config.STATE_PAUSED:
                stateStr = "paused";
                break;
            case Config.STATE_STOPPED:
                stateStr = "stopped";
                break;
            case Config.STATE_DESTROYED:
                stateStr = "destroyed";
                break;
        }
        return stateStr;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActivityState{" +
                "mState=" + parseState(mState) +
                '}';
    }

    public class Config {
        public static final int STATE_CREATED = 0;
        public static final int STATE_STARTED = 1;
        public static final int STATE_RESUMED = 2;
        public static final int STATE_PAUSED = 3;
        public static final int STATE_STOPPED = 4;
        public static final int STATE_SAVE_INSTANCE_STATE = 6;
        public static final int STATE_DESTROYED = 5;
    }
}
