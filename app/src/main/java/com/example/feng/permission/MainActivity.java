package com.example.feng.permission;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void permissionClick(View view) {
        switch (view.getId()) {
            case R.id.tv_main_camera_permission:
                String[] camera = new String[]{Manifest.permission.CAMERA};
                PermissionHandler.getInstance().request(this, camera, new IPermissionCallback() {
                    @Override
                    public void onPermissionCallback(boolean isGranted, int requestCode) {
                        Log.d("PermissionHandler", "onPermissionCallback: camera");
                    }
                });
                break;
            case R.id.tv_main_more_permission:
                //注：在android8.0之后，用户授权某个权限后，不能获取该权限组的其它权限
                //如，授予WRITE_EXTERNAL_STORAGE权限后，还需要请求READ_EXTERNAL_STORAGE，
                // 并且READ_EXTERNAL_STORAGE不再需要用户授权
                String[] morePermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
                PermissionHandler.getInstance().request(this, morePermission, new IPermissionCallback() {
                    @Override
                    public void onPermissionCallback(boolean isGranted, int requestCode) {
                        Log.d("PermissionHandler", "onPermissionCallback: morePermission");
                    }
                });
                break;
            default:
                break;
        }
    }

    private void openFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, ExampleFragment.create());
        transaction.commitNowAllowingStateLoss();
    }
}
