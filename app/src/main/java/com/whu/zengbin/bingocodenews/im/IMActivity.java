package com.whu.zengbin.bingocodenews.im;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.im.biz.ITalk;
import com.whu.zengbin.bingocodenews.im.biz.impl.TalkPresenter;

public class IMActivity extends BaseActivity implements ITalk.ITalkView {
  Toolbar mToolbar;
  IMFragment imFragment;
  ITalk.ITalkPresenter mPresenter;
  Bundle imExtras;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im);
    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);

    setPresenter(new TalkPresenter());
    imExtras = getIntent().getExtras();
    initNormalToolBar(mToolbar, R.string.talk, true);
    initIMFragment(imExtras);
  }

  @Override
  public void setPresenter(ITalk.ITalkPresenter presenter) {
    mPresenter = presenter;
  }

  private void initIMFragment(Bundle imExtras) {
    imFragment = imFragment.newInstance(imExtras);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.im_fragment_container, imFragment);
    ft.commitAllowingStateLoss();
  }
}
