package com.whu.zengbin.bingocodenews.im.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.whu.zengbin.bingocodenews.im.bean.Msg;

/**
 * 创建时间: 2018/10/12 16:49 <br>
 * 作者: zengbin <br>
 * 描述: 消息处理
 */
public interface ItemHandler {
  void bindView(@NonNull View convertView, @NonNull Msg msg, int position);

  View getView(ViewGroup parent);
}
