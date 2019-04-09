package com.example.feng.permission;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by feng on 2019/3/29 0029
 * 使用Fragment请求权限
 */
public class PermissionFragment extends Fragment {

    /**
     * log
     */
    private static final String TAG = PermissionFragment.class.getSimpleName();
    private boolean isLog = true;


    /**
     * 权限请求结果回调接口
     */
    private IPermissionCallback mCallback;

    /**
     * 权限请求code
     * */
    private int mRequestCode;

    public static PermissionFragment newInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    /**
     * 发起权限请求
     * */
    protected void requestPermission(IPermissionCallback callback, String[] permissionArray, int requestCode) {
        this.mCallback = callback;
        this.mRequestCode = requestCode;

        requestPermissions(permissionArray, requestCode);
    }


    /**
     * 权限请求结果
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isSuccess = true;
        for (int i = 0; i < grantResults.length; i++) {
            log("onRequestPermissionsResult: " + grantResults[i]);
            if (grantResults[i] != 0) {
                isSuccess = false;
                break;
            }
        }

        if (isSuccess) {//用户授权
            onPermissionCallback(true);
        } else {//未授权
            onPermissionCallback(false);
            log("fragment打开系统的权限管理页面");
            //打开权限管理
//            PermissionDialog.create(getActivity()).beginPermissionDialog();
        }
    }

    public void onPermissionCallback(boolean isGranted) {
        if (mCallback != null) {
            mCallback.onPermissionCallback(isGranted, mRequestCode);
        }
    }

    private void log(String log) {
        if (isLog) {
            Log.d("PermissionHandler", log);
        }
    }
}
