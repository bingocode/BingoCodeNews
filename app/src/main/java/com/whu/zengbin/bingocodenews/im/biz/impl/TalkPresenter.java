package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.content.Context;
import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.biz.BaseTalkPresenter;
import com.whu.zengbin.bingocodenews.im.biz.ITalk;

/**
 * 创建时间: 2018/08/07 10:24 <br>
 * 作者: zengbin <br>
 * 描述: 会话界面业务逻辑
 */
public class TalkPresenter implements ITalk.ITalkPresenter {
  private static final String TAG = "TalkPresenter";
  private WebClient mWebClient;
  private Context mContext;

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
    mWebClient.send(msg.msgContent);
  }

  @Override
  public void quitIM() {
    mWebClient.close();
  }

  @Override
  public void fetchIMMsgs() {

  }
}
