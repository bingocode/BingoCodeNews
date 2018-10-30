package com.whu.zengbin.bingocodenews.im.biz;

import com.whu.zengbin.bingocodenews.im.bean.Msg;

/**
 * 创建时间: 2018/10/22 11:48 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface MsgListener {
  /**
   * 收到消息
   *
   * @param msg
   */
  void onMsgUpdated(Msg msg);
}
