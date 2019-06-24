package com.jerryjin.kit.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Jerry
 * Generated at: 2019/5/29 9:47
 * WeChat: enGrave93
 * Description:
 */
@SuppressWarnings({"WeakerAccess"})
public class FragmentServant {

    private static final String TAG = "FragmentServant";
    private static final boolean DEBUG = true;
    private WeakReference<FragmentActivity> fragmentActivityWeakReference;
    private FragmentManager manager;
    private List<String> fragmentTags = new ArrayList<>();

    private FragmentServant() {
    }

    /**
     * Create a fragment by using newInstance method.
     * <br/>
     * You have to implement newInstance method or it will not create it.
     *
     * @param fClass The dst fragment class.
     * @param args   Bundle.
     * @return The generated fragment.
     */
    public static Fragment createFragment(Class<? extends Fragment> fClass, Bundle args) {
        return createFragment(fClass, "newInstance", args);
    }

    /**
     * Create a fragment by using a customized static method.
     *
     * @param fClass                The dst fragment class.
     * @param newInstanceMethodName The title of the customized method.
     * @param bundle                Bundle.
     * @return The generated fragment.
     */
    public static Fragment createFragment(Class<? extends Fragment> fClass, String newInstanceMethodName, @Nullable Bundle bundle) {
        try {
            Method method = fClass.getMethod(newInstanceMethodName, Bundle.class);
            Fragment fragment = (Fragment) method.invoke(fClass.newInstance(), bundle);
            if (DEBUG) {
                Log.d(TAG, fClass.getSimpleName() + " has been created by using method " + method.getName() + ".");
            }
            return fragment;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "NoSuchMethodException!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "IllegalAccessException!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.e(TAG, "InvocationTargetException!");
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e(TAG, "InstantiationException!");
        }
        return null;
    }

    public static Fragment createFragmentByConstructor(Class<? extends Fragment> fClass, Class[] parameterTypes, Object... objects) {
        try {
            Constructor constructor = fClass.getConstructor(parameterTypes);
            Fragment fragment = (Fragment) constructor.newInstance(objects);
            if (DEBUG) {
                Log.d(TAG, fClass.getSimpleName() + " has been created by using constructor method.");
            }
            return fragment;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "NoSuchMethodException!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "IllegalAccessException!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.e(TAG, "InvocationTargetException!");
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e(TAG, "InstantiationException!");
        }
        return null;
    }

    public static FragmentServant with(FragmentActivity activity) {
        FMH.instance.init(activity);
        return FMH.instance;
    }

    public void showDialogFragment(Fragment fragment) {
        manager.beginTransaction()
                .add(fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    public void showFragmentAllowStateLoss(Fragment fragment, String tag) {
        showFragment(fragment, tag, true);
    }

    public void showFragment(Fragment fragment, String tag, boolean allowStateLoss) {
        FragmentTransaction transaction = manager
                .beginTransaction()
                .add(fragment, tag);
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    private String generateFragmentTag(Fragment fragment) {
        UUID uuid = UUID.fromString(fragment.getClass().getCanonicalName());
        return uuid.toString();
    }

    public void registerFragmentLifecyclerCallbacks() {

    }

    public void hideFragment(String id) {

    }

    private void init(FragmentActivity activity) {
        fragmentActivityWeakReference = new WeakReference<>(activity);
        manager = activity.getSupportFragmentManager();
    }

    public void release() {
        if (fragmentActivityWeakReference != null) {
            fragmentActivityWeakReference.clear();
            fragmentActivityWeakReference = null;
        }
    }

    private static class FMH {
        private static final FragmentServant instance = new FragmentServant();
    }
}
