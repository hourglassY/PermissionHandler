package com.example.feng.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by feng on 2019/4/9 0009
 */
public class ExampleFragment extends Fragment{

    public static ExampleFragment create(){
        return new ExampleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_example, container,false);

        TextView textView = rootView.findViewById(R.id.tv_example_permission);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] camera = new String[]{Manifest.permission.CAMERA};
                PermissionHandler.getInstance().request(ExampleFragment.this, camera, new IPermissionCallback() {
                    @Override
                    public void onPermissionCallback(boolean isGranted, int requestCode) {
                        Log.d("PermissionHandler", "onPermissionCallback: fragment中的权限请求回调结果");
                    }
                });
            }
        });
        return rootView;
    }
}
