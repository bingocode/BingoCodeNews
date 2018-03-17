package com.whu.zengbin.bingocodenews.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.whu.zengbin.bingocodenews.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zengbin on 2018/3/11.
 */

public class DownLoadImageService implements Runnable {
    private static final String TAG = "BC-DownLoadImageService";
    private String url;
    private Context context;
    private File currentFile;
    private String title;
    public DownLoadImageService(Context context, String url,String title) {
        super();
        this.url = url;
        this.context = context;
        this.title = title;
    }

    @Override
    public void run() {
        Bitmap bmp = null;
        try {
            bmp = Picasso.get().load(url).get();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        CommonUtil.saveImageToGallery(context,bmp,title);
    }


}