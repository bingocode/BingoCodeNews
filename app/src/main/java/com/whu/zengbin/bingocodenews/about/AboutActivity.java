package com.whu.zengbin.bingocodenews.about;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.common.CommonUtil;

public class AboutActivity extends BaseActivity {
    Toolbar mToolbar;
    TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mVersion = (TextView) findViewById(R.id.version);
        initNormalToolBar(mToolbar,R.string.about,true);

        mVersion.setText(CommonUtil.getVerName(this.getApplicationContext()));
    }


}
