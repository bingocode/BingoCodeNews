package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.content.Context;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.common.*;
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
  private int page = 0;
  private ITalk.ITalkView mTalkView;

  public TalkPresenter(ITalk.ITalkView view) {
    this.mTalkView = view;
  }

  /**
   *  第一次进入会话拉取最近聊天记录回调
   */
  private CallBackListener<ResponseBody> mInitCallBack = new CallBackListener<ResponseBody>() {
    @Override
    public void onResponse(ResponseBody response) {
      try {
        String jsonStr = response.string();
        LogUtil.i(TAG, "mInitCallBack response = " + jsonStr);
        MsgListResult msgListResult = JsonUtil.fromJson(jsonStr, MsgListResult.class);
        mTalkView.addMsgs(msgListResult.msgList);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    @Override
    public void onError(Throwable e) {
      LogUtil.i(TAG, "mInitCallBack error: ");
      ToastUtil.toastShort("获取消息失败");
    }
  };

  /**
   * 拉取历史聊天记录回调
   */
  private CallBackListener<ResponseBody> mFetchCallBack = new CallBackListener<ResponseBody>() {
    @Override
    public void onResponse(ResponseBody response) {
      try {
        String jsonStr = response.string();
        LogUtil.i(TAG, "fetchIMMsgs response = " + jsonStr);
        MsgListResult msgListResult = JsonUtil.fromJson(jsonStr, MsgListResult.class);
        mTalkView.addMsgs(msgListResult.msgList);
      } catch (IOException e) {
        page--;
        e.printStackTrace();
      } finally {
        mTalkView.onRefreshComplete();
      }
    }
    @Override
    public void onError(Throwable e) {
      page--;
      LogUtil.i(TAG, "fetchIMMsgs error: ");
      ToastUtil.toastShort("获取消息失败");
      mTalkView.onRefreshComplete();
    }
  };


  @Override
  public void enterIM() {
    CodeNewsApp.getInstance().getmIMImpl().fetchIMMsgs(0, mInitCallBack);
  }

  @Override
  public void sendIMMsg(Msg msg) {
    CodeNewsApp.getInstance().getmIMImpl().sendIMMsg(msg, null);
  }

  @Override
  public void quitIM() {
    // CodeNewsApp.getInstance().getWebClient().close(); //断开websocket
  }

  @Override
  public void fetchIMMsgs() {
    CodeNewsApp.getInstance().getmIMImpl().fetchIMMsgs(page++, mFetchCallBack);
  }
}
