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
    public DownLoadImageService(Context context, String url) {
        super();
        this.url = url;
        this.context = context;
    }
    private CropSquareTransformation mCropSquareTransformation= new CropSquareTransformation();

    @Override
    public void run() {
        Log.i(TAG, "run");
        try {
//            Picasso.get()
//                    .load(url)
//                    .transform(mCropSquareTransformation)
//                    .into(bitmap);
            Resources res = context.getResources();
            Bitmap    bmp = BitmapFactory.decodeResource(res, R.drawable.ic_user_background);
            saveImageToGallery(context,bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = CommonUtil.getAppName(context);
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    currentFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            Log.i(TAG, "download result null");
            Bitmap result = Bitmap.createBitmap(source);
            if (result != null){
                // 在这里执行图片保存方法
                Log.i(TAG, "download result" +result);
                saveImageToGallery(context,result);
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
}