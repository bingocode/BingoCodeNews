package com.whu.zengbin.bingocodenews.im.biz;

import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.im.bean.BaseResponse;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgListResponse;

/**
 * 创建时间: 2018/10/22 11:49 <br>
 * 作者: zengbin <br>
 * 描述: IM接口
 */
public interface IIM {
  /**
   * 进入IM
   *
   * @param topic         需要监听的topic
   * @param IMMsgListener 新消息到达监听
   * @param callback      结果回调
   */
  void enterIM(long topic, MsgListener IMMsgListener,
      CallBackListener callback);

  /**
   * 退出IM
   *
   * @param topic         需要取消监听的topic
   * @param IMMsgListener 新消息到达监听
   * @param callback      结果回调
   */
  void quitIM(long topic, MsgListener IMMsgListener,
      CallBackListener<BaseResponse> callback);

  /**
   * 发送IM消息
   *
   * @param msg      IM消息对象
   * @param callBack 请求回调
   */
  void sendIMMsg(Msg msg, CallBackListener callBack);

  /**
   * 拉取消息
   *
   * @param page     指定要拉取的topic
   * @param callBack 请求回调
   */
  void fetchIMMsgs(int page, CallBackListener<MsgListResponse> callBack);
}
