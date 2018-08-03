package com.whu.zengbin.bingocodenews.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.R;

public class IMActivity extends BaseActivity {
  Toolbar mToolbar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im);
    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    initNormalToolBar(mToolbar,R.string.talk,true);
  }

}
