package com.whu.zengbin.bingocodenews.im.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建时间: 2018/10/12 18:23 <br>
 * 作者: zengbin <br>
 * 描述:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ MsgLocation.MSG_LEFT, MsgLocation.MSG_MIDDLE, MsgLocation.MSG_RIGHT })
public @interface MsgLocation {
  /**
   * 消息在左侧
   */
  int MSG_LEFT = 0;

  /**
   * 消息在中间
   */
  int MSG_MIDDLE = 1;

  /**
   * 消息在右侧
   */
  int MSG_RIGHT = 2;
}
