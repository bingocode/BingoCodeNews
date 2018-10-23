package com.whu.zengbin.bingocodenews.im.tool;

import android.location.Location;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgLocation;

/**
 * 创建时间: 2018/10/18 18:22 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class IMHelper {
  public static final String IS_SYSTEM = "System";
  public static final String IS_ME = "me";

  public static int getMsgLocation(Msg msg) {
    switch (msg.msgFrom) {
      case IS_ME: return MsgLocation.MSG_LEFT;
      case IS_SYSTEM: return MsgLocation.MSG_MIDDLE;
      default: return MsgLocation.MSG_LEFT;
    }
  }
}
