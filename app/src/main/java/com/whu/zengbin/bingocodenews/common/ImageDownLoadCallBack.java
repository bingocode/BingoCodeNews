package com.whu.zengbin.bingocodenews.common;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by zengbin on 2018/3/11.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}