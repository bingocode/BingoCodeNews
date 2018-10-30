package com.whu.zengbin.bingocodenews.im.tool;

import android.location.Location;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgLocation;
import com.whu.zengbin.bingocodenews.im.bean.MsgType;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 创建时间: 2018/10/18 18:22 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class IMHelper {
  private static final AtomicLong LOCAL_MSG_ID = new AtomicLong(System.currentTimeMillis());
  public static final String IS_SYSTEM = "System";
  public static final String IS_ME = "me";

  public static Msg buildMsg(String msgContent, int msgType, String msgFrom) {
    Msg msg = new Msg();
    msg.msgContent = msgContent;
    msg.msgId = System.currentTimeMillis();
    msg.deviceId = CommonUtil.getUniqueId();
    msg.msgType = msgType;
    msg.msgFrom = msgFrom;
    return msg;
  }

  public static int getMsgLocation(Msg msg) {
    if (IS_SYSTEM.equals(msg.msgFrom)) {
      return MsgLocation.MSG_MIDDLE;
    }
    if (CommonUtil.getUniqueId().equals(msg.deviceId)) {
      return MsgLocation.MSG_RIGHT;
    } else {
      return MsgLocation.MSG_LEFT;
    }
  }
}
