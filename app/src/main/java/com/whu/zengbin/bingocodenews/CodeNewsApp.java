package com.whu.zengbin.bingocodenews;

import android.app.Application;
import android.util.Log;

import com.whu.zengbin.bingocodenews.common.ThreadUtil;

/**
 * Created by zengbin on 2017/12/29.
 */

public class CodeNewsApp extends Application {
    private static final String TAG = "CodeNewsApp";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Application onCreate");
        ThreadUtil.startup();

    }
}
