package com.whu.zengbin.bingocodenews.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by zengbin on 2018/2/16.
 */

public class CommonUtil {
    private static final String TAG = "CommonUtil";

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
    public static String getIpAddress(Context context){
        NetworkInfo info = ((ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        String ip = null;
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                ip = inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                ip = ipAddress;
            }  else if (info.getType() == ConnectivityManager.TYPE_ETHERNET){
                // 有限网络
                ip = getLocalIp();
            }
        }
        Log.i(TAG, "ipaddress = " + ip);
        return ip;
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
            ((ip >> 8) & 0xFF) + "." +
            ((ip >> 16) & 0xFF) + "." +
            (ip >> 24 & 0xFF);
    }


    // 获取有限网IP
    private static String getLocalIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                    .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                        && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "0.0.0.0";
    }

    /**
     * 获取手机唯一标识
     * @return
     */
    public static String getUniqueId(){
        String uniqueId = SpUtil.getInstance().getUniqueId();
        if (TextUtils.isEmpty(uniqueId)) {
            String androidID =
                Settings.Secure.getString(CodeNewsApp.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueId = androidID + Build.SERIAL;
            try {
                uniqueId = toMD5(uniqueId);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SpUtil.getInstance().setUniqueId(uniqueId);
        }
        return uniqueId;
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}
