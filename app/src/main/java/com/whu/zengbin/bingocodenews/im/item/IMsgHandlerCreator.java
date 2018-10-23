package com.whu.zengbin.bingocodenews.im.item;

import android.content.Context;

/**
 * 创建时间: 2018/10/18 17:46 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface IMsgHandlerCreator {
  BaseItemHandler createHandler(Context context);
}
