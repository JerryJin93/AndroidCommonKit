package com.jerryjin.kit.lifecycle;

import android.app.Activity;

/**
 * Author: Jerry
 * Generated at: 2019/5/8 15:29
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public interface ApplicationState {
    int getItemCount();

    boolean isVisibleToUser(Activity activity);

    Activity getCurrent();
}
