package com.whu.zengbin.bingocodenews.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 创建时间: 2018/08/16 10:05 <br>
 * 作者: zengbin <br>
 * 描述:监听是否为移动网络广播
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
  private NetWorkChangeListener mListener;

  public NetworkChangeReceiver(NetWorkChangeListener listener) {
    this.mListener = listener;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    ConnectivityManager connectivity = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (info != null && info.isConnected()) {
        if (mListener != null) {
          mListener.onNetWorkChanged();
        }
      }
    }
  }

  interface NetWorkChangeListener {
    void onNetWorkChanged();
  }
}


