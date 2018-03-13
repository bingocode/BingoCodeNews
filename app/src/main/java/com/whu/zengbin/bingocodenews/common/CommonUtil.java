package com.whu.zengbin.bingocodenews.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.whu.zengbin.bingocodenews.R;

/**
 * Created by zengbin on 2018/2/16.
 */

public class CommonUtil {
    private static final String TAG = "BC-CommonUtil";

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

    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(verName)) {
            verName = "1.1.1";
        }
        return verName;
    }

    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
