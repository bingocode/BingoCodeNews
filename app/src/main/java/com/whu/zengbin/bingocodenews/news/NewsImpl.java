package com.whu.zengbin.bingocodenews.news;

import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 创建时间: 2018/10/23 11:48 <br>
 * 作者: zengbin <br>
 * 描述: 资讯逻辑实现
 */
public class NewsImpl implements INews {

  @Override
  public void getNewsInfoList(String catagory, int page, final CallBackListener callback) {
    NetWorkMrg.getInstance().getNewsInfoApi().getNewsInfoList(catagory, page)
        .subscribeOn(ThreadUtil.getIMScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseBody>() {
          @Override
          public void accept(ResponseBody responseBody) throws Exception {
            callback.onResponse(responseBody);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            callback.onError(throwable);
          }
        });
  }

  @Override
  public void getNewsSearchResultList(String catagory, int page, String content, final CallBackListener callback) {
    NetWorkMrg.getInstance().getNewsInfoApi().getNewsSearchResultList(catagory, page, content)
        .subscribeOn(ThreadUtil.getIMScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseBody>() {
          @Override
          public void accept(ResponseBody responseBody) throws Exception {
            callback.onResponse(responseBody);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            callback.onError(throwable);
          }
        });
  }
}
