package com.whu.zengbin.bingocodenews.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by zengbin on 2018/3/13.
 */

public class PermissionUtil {
    private static final String TAG = "PermissionUtil";
    public static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static void requestionPermission(Activity context, String PermissionStr) {
        //检查写入权限
        if (ContextCompat.checkSelfPermission(context,PermissionStr) == PackageManager.PERMISSION_GRANTED) {
            //拥有读写文件权限
            LogUtil.i(TAG,"拥有读写文件权限");
        } else {
            LogUtil.i(TAG,"没有读写权限");
            ActivityCompat.requestPermissions(context,new String[]{PermissionStr},
                    140);
        }
    }

    public static boolean isgetPurmission(Activity context, String permissionStr) {
        return ContextCompat.checkSelfPermission(context,permissionStr) == PackageManager.PERMISSION_GRANTED;
    }
}
