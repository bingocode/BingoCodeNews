package com.whu.zengbin.bingocodenews;

import android.app.Application;

import com.bingo.greendao.gen.DaoMaster;
import com.bingo.greendao.gen.DaoSession;
import com.bumptech.glide.request.target.ViewTarget;
import com.whu.zengbin.bingocodenews.common.LogUtil;
import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
import com.whu.zengbin.bingocodenews.news.NewsImpl;

/**
 * Created by zengbin on 2017/12/29.
 */

public class CodeNewsApp extends Application {
    private static final String TAG = "CodeNewsApp";
    private static DaoSession mDaoSession;
    protected static CodeNewsApp mInstance;
    private NewsImpl mNewsImpl;
    private NetWorkMrg mNetNetWorkMrg;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG,"Application onCreate");
        mInstance = this;
        ThreadUtil.startup();
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this,"code_news.db").getWritableDatabase()).newSession();
        ViewTarget.setTagId(R.id.tag_glide);
        mNewsImpl = new NewsImpl();
        mNetNetWorkMrg = NetWorkMrg.getInstance();
        mNetNetWorkMrg.init();
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

    public NewsImpl getmNewsImpl() {
        return mNewsImpl;
    }
}
