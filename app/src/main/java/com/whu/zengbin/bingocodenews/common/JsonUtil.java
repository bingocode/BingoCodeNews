package com.whu.zengbin.bingocodenews.common;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 创建时间: 2018/10/25 17:56 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class JsonUtil {
  private static final Gson sGson = new Gson();

  @Nullable
  public static Map<String, String> fromJsonToMap(String mapJson) {
    if(!TextUtils.isEmpty(mapJson)) {
      JSONObject jsonObject = null;

      try {
        jsonObject = new JSONObject(mapJson);
      } catch (JSONException var7) {
        var7.printStackTrace();
        return null;
      }

      Iterator<String> iterator = jsonObject.keys();
      if(iterator != null) {
        HashMap map = new HashMap();

        while(iterator.hasNext()) {
          String key = (String)iterator.next();
          String value = null;

          try {
            value = jsonObject.getString(key);
          } catch (JSONException var8) {
            var8.printStackTrace();
            continue;
          }

          map.put(key, value);
        }

        return map;
      }
    }

    return null;
  }

  public static <T> T fromJson(@Nullable String json, Class<T> clazz) {
    return sGson.fromJson(json, clazz);
  }

  public static String toJson(@Nullable Object src) {
    return sGson.toJson(src);
  }

  @Nullable
  public static String toJson(Map<String, String> map) {
    if(map != null) {
      JSONObject jsonObject = new JSONObject(map);
      String mapJson = jsonObject.toString();
      return mapJson;
    } else {
      return null;
    }
  }

}
