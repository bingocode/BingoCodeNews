package com.whu.zengbin.bingocodenews.im.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建时间: 2018/10/18 17:42 <br>
 * 作者: zengbin <br>
 * 描述:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ MsgType.MSG_TEXT })
public @interface MsgType {
  /**
   * 文本消息
   */
  int MSG_TEXT= 0;
}
