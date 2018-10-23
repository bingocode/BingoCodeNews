package com.whu.zengbin.bingocodenews.im;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 创建时间: 2018/10/12 16:01 <br>
 * 作者: zengbin <br>
 * 描述: 输入框
 */
public class IMInputLayout {

  static class IMInputViewHolder {
    final View mRootView;

    public IMInputViewHolder(View rootView) {
      mRootView = rootView;
      //findViewById
    }
  }

  interface IconClickCallBack {
    void onSendClick();
  }
}
