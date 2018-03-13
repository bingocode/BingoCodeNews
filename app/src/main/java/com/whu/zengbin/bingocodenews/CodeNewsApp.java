package com.whu.zengbin.bingocodenews;

import android.app.Application;
import android.util.Log;

import com.bingo.greendao.gen.DaoMaster;
import com.bingo.greendao.gen.DaoSession;
import com.bumptech.glide.request.target.ViewTarget;
import com.whu.zengbin.bingocodenews.common.ThreadUtil;

/**
 * Created by zengbin on 2017/12/29.
 */

public class CodeNewsApp extends Application {
    private static final String TAG = "CodeNewsApp";
    private static DaoSession mDaoSession;
    protected static CodeNewsApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Application onCreate");
        ThreadUtil.startup();
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this,"code_news.db").getWritableDatabase()).newSession();
        ViewTarget.setTagId(R.id.tag_glide);
    }

    public CodeNewsApp(){
        mInstance = this;
    }

    public static DaoSession getmDaoSession(){
        return mDaoSession;
    }

    public static CodeNewsApp getInstance(){
        if (mInstance == null) {
            throw new IllegalStateException("No CodeNewsApp here!");
        }
        return mInstance;
    }
}
