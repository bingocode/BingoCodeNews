package com.whu.zengbin.bingocodenews.im.presenter;

/**
 * 创建时间: 2018/08/07 10:19 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface ITalk {

  interface ITalkView {
    void setPresenter(ITalkPresenter presenter);


  }

  interface ITalkPresenter {
    void sendMsg();
  }

}
