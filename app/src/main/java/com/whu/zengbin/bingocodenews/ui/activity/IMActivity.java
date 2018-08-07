package com.whu.zengbin.bingocodenews.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.presenter.ITalk;
import com.whu.zengbin.bingocodenews.presenter.impl.TalkPresenter;

public class IMActivity extends BaseActivity implements ITalk.ITalkView {
  Toolbar mToolbar;
  Button mSend;
  ITalk.ITalkPresenter mPresenter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im);
    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    mSend = (Button) findViewById(R.id.send);
    initNormalToolBar(mToolbar,R.string.talk,true);
    setPresenter(new TalkPresenter());
    mSend.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mPresenter.sendMsg();
      }
    });
  }

  @Override public void setPresenter(ITalk.ITalkPresenter presenter) {
    mPresenter = presenter;
  }
}
