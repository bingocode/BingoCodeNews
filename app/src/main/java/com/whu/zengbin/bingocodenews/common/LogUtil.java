package com.whu.zengbin.bingocodenews.common;

import android.util.Log;

/**
 * 创建时间: 2018/08/07 10:55 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class LogUtil {
  public static final String PRE = "BC-";
  public static void v (String o, String s) {
    Log.v(PRE + o,s);
  }
  public static void d (String o, String s) {
    Log.d(PRE + o,s);
  }
  public static void i (String o, String s) {
    Log.i(PRE + o,s);
  }
  public static void w (String o, String s) {
    Log.w(PRE + o,s);
  }
  public static void e (String o, String s) {
    Log.e(PRE + o,s);
  }
}
