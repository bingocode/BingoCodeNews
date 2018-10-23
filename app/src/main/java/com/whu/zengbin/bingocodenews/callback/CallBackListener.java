package com.whu.zengbin.bingocodenews.callback;

/**
 * 创建时间: 2018/10/22 11:56 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface CallBackListener<T> {

  /**
   * 发生响应
   *
   * @param response 响应体
   */
  void onResponse(T response);

  /**
   * 发生错误
   *
   * @param e 异常
   */
  void onError(Throwable e);
}