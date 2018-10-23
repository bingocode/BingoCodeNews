package com.whu.zengbin.bingocodenews.im.bean;

/**
 * 创建时间: 2018/10/22 12:11 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class BaseResponse<T> {
  /**
   * 错误码。0代表无错误
   */
  public int errno;

  /**
   * 数据
   */
  public T data;
}