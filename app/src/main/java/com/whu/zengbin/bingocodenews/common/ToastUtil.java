package com.whu.zengbin.bingocodenews.common;

import android.widget.Toast;
import com.whu.zengbin.bingocodenews.CodeNewsApp;

/**
 * 创建时间: 2018/10/30 16:24 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class ToastUtil {

  public static void toastShort(String text) {
    Toast.makeText(CodeNewsApp.getInstance(), text, Toast.LENGTH_SHORT).show();
  }

}
