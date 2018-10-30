package com.whu.zengbin.bingocodenews.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;

import com.whu.zengbin.bingocodenews.network.api.IMApi;
import com.whu.zengbin.bingocodenews.network.api.NewsInfoApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zengbin on 2018/2/15.
 */

public class NetWorkMrg {
  private volatile static NetWorkMrg sInstance = null;
  private NewsInfoApi mNewsInfoApi;
  private IMApi mImApi;

  private NetWorkMrg() {
  }

  public static NetWorkMrg getInstance() {
    if (sInstance == null) {
      synchronized (NetWorkMrg.class) {
        if (sInstance == null) {
          sInstance = new NetWorkMrg();
        }
      }
    }
    return sInstance;
  }

  public void init() {
    mNewsInfoApi = createApi(ConstraintUtil.BASE_NEWS_URL, NewsInfoApi.class);
    mImApi = createApi(ConstraintUtil.BASE_IM_URL, IMApi.class);
  }

  public static <S> S createApi(String baseUri, Class<S> apiClass) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUri)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    return retrofit.create(apiClass);
  }

  public NewsInfoApi getNewsInfoApi() {
    return mNewsInfoApi;
  }

  public IMApi getIMApi() {
    return mImApi;
  }
}
