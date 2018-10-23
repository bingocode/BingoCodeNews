package com.whu.zengbin.bingocodenews.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;

import com.whu.zengbin.bingocodenews.event.NewsInfo;
import com.whu.zengbin.bingocodenews.network.api.IMApi;
import com.whu.zengbin.bingocodenews.network.api.NewsInfoApi;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    mNewsInfoApi = createApi(ConstraintUtil.BASE_URL, NewsInfoApi.class);
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
}
