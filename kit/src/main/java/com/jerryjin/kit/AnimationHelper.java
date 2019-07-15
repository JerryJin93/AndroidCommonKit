package com.jerryjin.kit;

/**
 * Author: Jerry
 * Generated at: 2019/7/15 16:04
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class AnimationHelper {

    private static final String TAG = "AnimationHelper";
    private static final boolean DEBUG = false;

    public static final int BUTTER_FPS = 60;

    public static float computeAnimDuration(float fps, int frames) {
        return frames / fps;
    }

    public static float getSuggestedAnimDuration(int frames) {
        return computeAnimDuration(BUTTER_FPS, frames);
    }

    public static float computeFrames(float fps, float duration) {
        return fps * duration;
    }

    public static int getSuggestedFrames(int duration) {
        return (int) Math.ceil(computeFrames(BUTTER_FPS, duration));
    }

    public static float computeFPS(float duration, int frames) {
        return frames / duration;
    }
}
