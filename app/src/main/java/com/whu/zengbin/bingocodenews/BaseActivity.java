package com.whu.zengbin.bingocodenews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by zengbin on 2017/12/29.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    /**
     * 初始化toolbar： 标题，是否显示返回上级按钮
     * @param mToolbar
     * @param TitleResId
     * @param displayHomeAsUp
     */
    public void initNormalToolBar(Toolbar mToolbar, int TitleResId, boolean displayHomeAsUp) {
        setSupportActionBar(mToolbar);
        if (displayHomeAsUp) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
        }
        mToolbar.setTitle(TitleResId);
    }

    public void initNormalToolBar(Toolbar mToolbar, String title, boolean displayHomeAsUp) {
        setSupportActionBar(mToolbar);
        if (displayHomeAsUp) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
        }
        mToolbar.setTitle(title);
    }
}
