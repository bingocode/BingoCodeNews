package com.whu.zengbin.bingocodenews.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.whu.zengbin.bingocodenews.R;

/**
 * Created by zengbin on 2018/2/16.
 */

public class CommonUtil {

    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    public static int getHolderIcon(String type){
        int result;
        switch (type) {
            case ConstraintUtil.ANDROID:
                result =  R.mipmap.icon_catagory_android;
                break;
            case ConstraintUtil.WEB:
                result =  R.mipmap.icon_catagory_web;
                break;
            case ConstraintUtil.VIDEO:
                result =  R.mipmap.icon_catagory_tv;
                break;
            default:
                result = R.mipmap.ic_launcher;
        }
        return result;
    }


    public static String flagConvertStr(int flag) {
        String result = ConstraintUtil.ANDROID;
        switch (flag) {
            case 0: result = ConstraintUtil.ANDROID;break;
            case 1: result = ConstraintUtil.WEB;break;
            case 2: result = ConstraintUtil.VIDEO;break;
            case 3: result = ConstraintUtil.PHOTO;break;
        }
        return result;
    }
}
