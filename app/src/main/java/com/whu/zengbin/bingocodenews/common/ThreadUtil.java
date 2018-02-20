package com.whu.zengbin.bingocodenews.common;


import android.os.Handler;

/**
 * Created by zengbin on 2018/1/24.
 */

public class ThreadUtil {
    private static Handler sUiHandler = null;

    public static void runOnUi(Runnable r) {
        sUiHandler.post(r);
    }
    public static void postOnUiDelayed(Runnable r, int delay) {
        sUiHandler.postDelayed(r,delay);
    }

    public static void startup() {
        sUiHandler = new Handler();
    }
}
