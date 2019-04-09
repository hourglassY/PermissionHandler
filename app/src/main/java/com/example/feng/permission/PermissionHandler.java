package com.example.feng.permission;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by feng on 2019/3/28 0028
 * 权限请求管理
 */
public class PermissionHandler {


    /**
     * 处理权限请求的fragment的TAG
     * 防止多次创建PermissionFragment对象
     * */
    private static final String FRAGMENT_TAG = "PermissionFragment";


    /**
     * 默认的requestCode
     */
    private static final int REQUEST_CODE_DEFAULT = 201;
    private int mRequestCode = REQUEST_CODE_DEFAULT;


    /**
     * 在Fragment中打开Fragment
     */
    public void request(Fragment fragment, String[] permissions, IPermissionCallback callback) {
        FragmentActivity activity = fragment.getActivity();
        request(activity, permissions, callback);
    }


    /**
     * 在Activity打开Fragment
     */
    public void request(FragmentActivity activity, String[] permissions, IPermissionCallback callback) {
        String unPermissions[] = PermissionUtils.unGranted(activity, permissions);

        if (unPermissions == null || unPermissions.length <= 0) {
            if (callback != null) {
                callback.onPermissionCallback(true, mRequestCode);
            }
        } else {
            for (int i = 0; i < unPermissions.length; i++) {
                Log.d("PermissionHandler", "request: unPermissions[]" + i + unPermissions[i]);
            }
            Log.d("PermissionHandler", "request: unPermissions[].length() = " + unPermissions.length);
            PermissionFragment fragment = findFragment(activity);
            fragment.requestPermission(callback, unPermissions, mRequestCode);
        }
    }

    /**
     * @param requestCode 权限请求的requestCode
     */
    public PermissionHandler setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }


    /**
     * 获取PermissionFragment对象
     */
    public PermissionFragment findFragment(FragmentActivity activity) {
        PermissionFragment fragment = null;
        if (activity != null && !activity.isFinishing()) {
            FragmentManager manager = activity.getSupportFragmentManager();
            fragment = (PermissionFragment) manager.findFragmentByTag(FRAGMENT_TAG);

            if (fragment == null) {
                fragment = PermissionFragment.newInstance();
                manager.beginTransaction()
                        .add(fragment, FRAGMENT_TAG)
                        .commitNow();
            }
        }
        return fragment;
    }


    /**
     * 使用单例模式获取PermissionHandler对象
     */
    private static final PermissionHandler INSTANCE = new PermissionHandler();

    public static PermissionHandler getInstance() {
        return INSTANCE;
    }

}
