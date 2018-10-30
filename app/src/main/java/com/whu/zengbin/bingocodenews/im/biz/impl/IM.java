package com.whu.zengbin.bingocodenews.im.biz.impl;

import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.im.bean.BaseResponse;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgListResponse;
import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.im.biz.IIM;
import com.whu.zengbin.bingocodenews.im.biz.MsgListener;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 创建时间: 2018/10/22 12:26 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class IM implements IIM {

  private volatile static IM mInstance;

  private IM() {
  }

  public static IM getInstance() {
    if (mInstance == null) {
      synchronized (IM.class) {
        if (mInstance == null) {
          mInstance = new IM();
        }
      }
    }
    return mInstance;
  }
  /**
   * 进入IM
   *
   * @param topic         需要监听的topic
   * @param IMMsgListener 新消息到达监听
   * @param callback      结果回调
   */
  @Override
  public void enterIM(long topic, MsgListener IMMsgListener, CallBackListener callback) {

  }

  /**
   * 退出IM
   *
   * @param topic         需要取消监听的topic
   * @param IMMsgListener 新消息到达监听
   * @param callback      结果回调
   */
  @Override
  public void quitIM(long topic, MsgListener IMMsgListener,
      CallBackListener<BaseResponse> callback) {

  }

  /**
   * 发送IM消息
   *
   * @param msg      IM消息对象
   * @param callBack 请求回调
   */
  @Override
  public void sendIMMsg(Msg msg, CallBackListener callBack) {

  }

  /**
   * 拉取消息
   *
   * @param page     拉取页数
   * @param callback     请求回调
   */
  @Override
  public void fetchIMMsgs(int page, final CallBackListener callback) {
    NetWorkMrg.getInstance().getIMApi().fetchRecentMsg(page)
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
