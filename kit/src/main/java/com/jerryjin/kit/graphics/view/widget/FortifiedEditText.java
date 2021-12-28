package com.jerryjin.kit.graphics.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Author: Jerry
 * Generated at: 2019/7/12 13:34
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class FortifiedEditText extends EditText {

    private static final String TAG = "FortifiedEditText";
    private static final boolean DEBUG = false;

    private OnSelectionChangedListener onSelectionChangedListener;
    private OnDelEnteredListener onDelEnteredListener;

    public FortifiedEditText(Context context) {
        super(context);
    }

    public FortifiedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FortifiedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (onSelectionChangedListener != null) {
            onSelectionChangedListener.onSelectionChanged(selStart, selEnd);
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new TrickyInputConnection(super.onCreateInputConnection(outAttrs), true);
    }

    public void setOnSelectionChangedListener(OnSelectionChangedListener onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }

    public void setOnDelEnteredListener(OnDelEnteredListener onDelEnteredListener) {
        this.onDelEnteredListener = onDelEnteredListener;
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(int selectionStart, int selectionEnd);
    }

    public interface OnDelEnteredListener {
        void onDelEntered();
    }

    private class TrickyInputConnection extends InputConnectionWrapper {

        /**
         * Initializes a wrapper.
         *
         * <p><b>Caveat:</b> Although the system can accept {@code (InputConnection) null} in some
         * places, you cannot emulate such a behavior by non-null {@link InputConnectionWrapper} that
         * has {@code null} in {@code target}.</p>
         *
         * @param target  the {@link InputConnection} to be proxied.
         * @param mutable set {@code true} to protect this object from being reconfigured to target
         *                another {@link InputConnection}.  Note that this is ignored while the target is {@code null}.
         */
        TrickyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if (onDelEnteredListener != null) {
                    onDelEnteredListener.onDelEntered();
                }
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }
}
