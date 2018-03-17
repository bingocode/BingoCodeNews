package com.whu.zengbin.bingocodenews.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.whu.zengbin.bingocodenews.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static void saveImageToGallery(final Context context, Bitmap bmp, String title) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), CommonUtil.getAppName(context));
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = title.replace('/', '-') + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            assert bmp != null;
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(file);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
        final String msg = "图片已保存在"+
                appDir.getAbsolutePath();
        ThreadUtil.runOnUi(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
