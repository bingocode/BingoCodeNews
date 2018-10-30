package com.whu.zengbin.bingocodenews.im.item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgLocation;

/**
 * 创建时间: 2018/10/12 18:34 <br>
 * 作者: zengbin <br>
 * 描述: 文字消息处理器
 */
public class TextMsgHandler extends BaseItemHandler {

  public TextMsgHandler(Context context, int msgLocation) {
    super(context, msgLocation);
  }

  @Override
  protected void bindViewData(@NonNull View convertView, @NonNull Msg msg, int position) {
    TextMsgViewHolder textMsgViewHolder = new TextMsgViewHolder(convertView);
    textMsgViewHolder.mMsgTextView.setText(msg.msgContent);
  }

  @Override
  public View getView(ViewGroup parent) {
    return super.getView(parent);
  }

  @Override
  protected int detailLayoutResId() {
    if (mMsgLocation == MsgLocation.MSG_LEFT) {
      return R.layout.item_im_left_text;
    } else if (mMsgLocation == MsgLocation.MSG_RIGHT) {
      return R.layout.item_im_right_text;
    } else {
      return R.layout.item_im_center_text;
    }
  }


  static class TextMsgViewHolder {

    TextView mMsgTextView;

    TextMsgViewHolder(View rootView) {
      mMsgTextView = (TextView)rootView.findViewById(R.id.im_item_textview);
    }
  }
}
