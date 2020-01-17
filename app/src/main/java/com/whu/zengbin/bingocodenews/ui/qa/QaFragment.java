package com.whu.zengbin.bingocodenews.ui.qa;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.common.ToastUtil;

public class QaFragment extends Fragment {

  private QaViewModel mViewModel;
  private Button mSend;
  private EditText mEdit;

  public static QaFragment newInstance() {
    return new QaFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.qa_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(QaViewModel.class);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mSend = (Button) view.findViewById(R.id.btn_send);
    mEdit = (EditText) view.findViewById(R.id.edit_input);
    mSend.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mViewModel.setMyWord(mEdit.getText().toString());
        mViewModel.getMyWord().observe(QaFragment.this, new Observer<String>() {
          @Override public void onChanged(@Nullable String s) {
            if (TextUtils.isEmpty(s)) {
              ToastUtil.toastShort("请输入内容");
            } else {
              ToastUtil.toastShort(s);
            }
          }
        });
      }
    });
  }
}
