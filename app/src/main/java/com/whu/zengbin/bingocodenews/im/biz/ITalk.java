package com.whu.zengbin.bingocodenews.im.biz;

import com.whu.zengbin.bingocodenews.im.bean.Msg;

/**
 * 创建时间: 2018/08/07 10:19 <br>
 * 作者: zengbin <br>
 * 描述: 界面逻辑接口
 */
public interface ITalk {

  interface ITalkView {
    void setPresenter(ITalkPresenter presenter);


  }

  interface ITalkPresenter {

    void enterIM();

    void sendIMMsg(Msg msg);

    void quitIM();

    void fetchIMMsgs();

  }

}
