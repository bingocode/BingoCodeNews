package com.whu.zengbin.bingocodenews.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bingo.greendao.gen.NewsInfoDao;
import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.WebviewActivity;
import com.whu.zengbin.bingocodenews.event.NewsInfo;
import com.whu.zengbin.bingocodenews.adapter.MyRecyclerViewAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    SwipeMenuRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
    Toolbar mToolbar;
    private List<NewsInfo> infolist = new ArrayList<>();
    final NewsInfoDao newsInfoDao = CodeNewsApp.getmDaoSession().getNewsInfoDao();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.id_recyclerview);
        initNormalToolBar(mToolbar, R.string.save,true);
        initData();
        configRecycleView();
        if (infolist.size() > 0) {
            Toast.makeText(this, "侧滑就可以删除干货哦", Toast.LENGTH_SHORT).show();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            findViewById(R.id.img_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        infolist = newsInfoDao.loadAll();
    }

    private void configRecycleView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(this,infolist);
        mRecyclerViewAdapter.setIsshowcollect(false);
        mRecyclerViewAdapter.setIsshowLoadmore(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(CollectionActivity.this, WebviewActivity.class);
                NewsInfo info = infolist.get(position);
                intent.putExtra(WebviewActivity.DESC_TITLE,info.getDesc());
                intent.putExtra(WebviewActivity.URL_CONTENT, info.getUrl());
                startActivity(intent);
            }
        });

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CollectionActivity.this);
             // 各种文字和图标属性设置。
                rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
            }
        };
        // 设置监听器。
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            }
        };
        // 菜单点击监听。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.setItemViewSwipeEnabled(true); // 策划删除，默认关闭。


        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getAdapterPosition();
                // Item被侧滑删除时，删除数据，并更新adapter。
                newsInfoDao.deleteByKey(infolist.get(position).getId());
                infolist.remove(position);
                mRecyclerViewAdapter.notifyItemRemoved(position);
                Toast.makeText(CollectionActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                if (infolist.size() <= 0) {
                    mRecyclerView.setVisibility(View.GONE);
                    findViewById(R.id.img_empty).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                }
            }
        };

        mRecyclerView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

}
