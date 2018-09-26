package com.whu.zengbin.bingocodenews.im.presenter.impl;

import com.whu.zengbin.bingocodenews.common.ThreadUtil;
import com.whu.zengbin.bingocodenews.im.presenter.ITalk;

/**
 * 创建时间: 2018/08/07 10:24 <br>
 * 作者: zengbin <br>
 * 描述: 会话界面业务逻辑
 */
public class TalkPresenter implements ITalk.ITalkPresenter {
  private static final String TAG = "TalkPresenter";

  @Override public void sendMsg() {
    ThreadUtil.threadPools.execute(new ClientThread());
  }


}
