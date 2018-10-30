package com.whu.zengbin.bingocodenews.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.whu.zengbin.bingocodenews.CodeNewsApp;

/**
 * 创建时间: 2018/10/25 15:42 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class SpUtil {
  private static final String NEWS_PREFERENCE_FILE_NAME = "bingo.news";
  private static final String KEY_UNIQUE_ID = "bingo.unique.id";
  private SharedPreferences mPreferences;

  public static SpUtil getInstance() {
    return InstanceHolder.instance;
  }
  private SpUtil() {
    mPreferences = CodeNewsApp.getInstance()
        .getSharedPreferences(NEWS_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
  }
  private static class InstanceHolder {
    static SpUtil instance = new SpUtil();
  }

  public String getUniqueId() {
    return mPreferences.getString(KEY_UNIQUE_ID,"");
  }

  public void setUniqueId(String uniqueId) {
    mPreferences.edit().putString(KEY_UNIQUE_ID, uniqueId).apply();
  }
}
