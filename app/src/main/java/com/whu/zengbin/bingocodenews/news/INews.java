package com.whu.zengbin.bingocodenews.news;

import com.whu.zengbin.bingocodenews.callback.CallBackListener;

/**
 * 创建时间: 2018/10/23 11:47 <br>
 * 作者: zengbin <br>
 * 描述: 资讯逻辑处理接口
 */
public interface INews {

  void getNewsInfoList(String catagory, int page, CallBackListener callback);

  void getNewsSearchResultList(String catagory, int page, String content, CallBackListener callback);
}
