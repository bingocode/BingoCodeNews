package com.whu.zengbin.bingocodenews.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zengbin on 2018/2/15.
 */

public class NetWorkMrg {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstraintUtil.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static void requestNews(String catagory, int page) {
        NewsInfoService service = retrofit.create(NewsInfoService.class);
        Observable<ResponseBody> observable = service.getNewsInfoList(catagory,page);
        observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Observer<ResponseBody>() {//订阅
                   @Override
                   public void onError(Throwable e) {
                       e.printStackTrace(); //请求过程中发生错误
                   }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                   public void onNext(ResponseBody book) {

                   }
               });


    }

    public static void requestNewsInfo(String catagory, int page, retrofit2.Callback<ResponseBody> callback) {
        NewsInfoService service = retrofit.create(NewsInfoService.class);
        Call<ResponseBody> call = service.getHomeNewsInfoList(catagory,page);
        call.enqueue(callback);
    }

    public static void requestNewsSearchList(String catagory, int page, String content, retrofit2.Callback<ResponseBody> callback) {
        NewsInfoService service = retrofit.create(NewsInfoService.class);
        Call<ResponseBody> call = service.getSearchResultList(catagory,page,content);
        call.enqueue(callback);
    }

}
