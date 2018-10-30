package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.content.Context;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.JsonUtil;
import com.whu.zengbin.bingocodenews.common.LogUtil;
import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgListResult;
import com.whu.zengbin.bingocodenews.im.biz.BaseTalkPresenter;
import com.whu.zengbin.bingocodenews.im.biz.ITalk;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * 创建时间: 2018/08/07 10:24 <br>
 * 作者: zengbin <br>
 * 描述: 会话界面业务逻辑
 */
public class TalkPresenter implements ITalk.ITalkPresenter {
  private static final String TAG = "TalkPresenter";
  private WebClient mWebClient;
  private Context mContext;
  private int page = 0;

  public TalkPresenter(Context context) {
    this.mContext = context;
  }

  @Override
  public void enterIM() {
    mWebClient = WebClient.getInstance();
    mWebClient.initWebSocket();
  }

  @Override
  public void sendIMMsg(Msg msg) {
    mWebClient.send(JsonUtil.toJson(msg));
  }

  @Override
  public void quitIM() {
    mWebClient.close();
  }

  @Override
  public void fetchIMMsgs(int page, CallBackListener<ResponseBody> callback) {
    CodeNewsApp.getInstance().getmIMImpl().fetchIMMsgs(page, callback);
  }
}
