package com.whu.zengbin.bingocodenews.im;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgType;
import com.whu.zengbin.bingocodenews.im.tool.IMHelper;
import org.greenrobot.eventbus.EventBus;

/**
 * 创建时间: 2018/10/12 16:01 <br>
 * 作者: zengbin <br>
 * 描述: 输入框
 */
public class IMInputLayout {
  static class IMInputViewHolder {
    final View mRootView;
    final EditText mInputEdit;
    final Button mSendBtn;

    public IMInputViewHolder(View rootView) {
      mRootView = rootView;
      mInputEdit = (EditText) rootView.findViewById(R.id.edit_input);
      mSendBtn = (Button) rootView.findViewById(R.id.btn_send);
    }
  }


  public static void initInputLayout(final IMInputViewHolder viewHolder, final InputCallBack callBack) {
    viewHolder.mSendBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String text = viewHolder.mInputEdit.getText().toString();
        if (!TextUtils.isEmpty(text)) {
          callBack.onSendClick(IMHelper.buildMsg(text, MsgType.MSG_TEXT, IMHelper.IS_ME));
          viewHolder.mInputEdit.setText("");
        }
      }
    });
  }
  public interface InputCallBack {
    void onSendClick(Msg msg);
  }
}
