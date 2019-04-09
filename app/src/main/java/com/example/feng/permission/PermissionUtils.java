package com.example.feng.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2019/3/29 0029
 * 权限请求Utils
 */
public class PermissionUtils {

    /**
     * 返回未授予的权限数组
     */
    public static String[] unGranted(Context context, String[] permissions) {
        if (permissions == null || !isNeedCheck(context)) {
            return null;
        }

        List<String> unGrantedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            Log.d("PermissionHandler", "AllPermission: " + permission);
            if (!isAccept(context, permission)) {
                unGrantedList.add(permission);
                Log.d("PermissionHandler", "unGranted: " + permission);
            }
        }

        Log.d("PermissionHandler", "unGranted: list.size() = " + unGrantedList.size());

        return unGrantedList.toArray(new String[]{});
    }


    /**
     * 权限是否已经获取
     */
    public static boolean isAccept(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 是否需要动态请求权限
     */
    public static boolean isNeedCheck(Context context) {
        int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        int sdkVersionM = Build.VERSION_CODES.M;
        return Build.VERSION.SDK_INT >= sdkVersionM && targetSdkVersion >= sdkVersionM;
    }
}
