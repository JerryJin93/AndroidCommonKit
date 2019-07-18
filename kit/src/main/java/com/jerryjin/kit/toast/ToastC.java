package com.jerryjin.kit.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Author: Jerry
 * Generated at: 2019-07-18 22:37
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: In memory of YCZ in high school.
 */
public class ToastC {

    private static final String TAG = "ToastC";
    private static final boolean DEBUG = false;

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_CUSTOM = 1;

    private Toast toast;
    private int type;
    private Context mContext;
    private View mContentView;


    public ToastC(Context context) {
        this.mContext = context;
        initToast(mContext);
    }

    private ToastC(Builder builder) {
        this.mContext = builder.context;
        initToast(mContext);
    }



    private void initToast(Context context) {
        toast = new Toast(context);
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    public void setText(CharSequence sequence) {
        toast.setText(sequence);
    }

    public void show(CharSequence sequence) {

    }

    public void show() {

        toast.show();
    }

    public void cancel() {
        toast.cancel();
    }


    public static class Builder {

        private Context context;
        private int layoutRes;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(int layoutRes) {
            this.layoutRes = layoutRes;
            return this;
        }

        public ToastC build() {
            return new ToastC(this);
        }
    }
}
