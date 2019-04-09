package com.example.feng.permission;

/**
 * Created by feng on 2019/3/29 0029
 */
public interface IPermissionCallback {

    void onPermissionCallback(boolean isGranted, int requestCode);
}
