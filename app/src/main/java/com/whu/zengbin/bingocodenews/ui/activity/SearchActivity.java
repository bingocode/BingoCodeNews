package com.whu.zengbin.bingocodenews.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;
import com.whu.zengbin.bingocodenews.listener.EndLessOnScrollListener;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
import com.whu.zengbin.bingocodenews.ui.adapter.SearchRecycleViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchRecycleViewAdapter.OnItemClickListener{
    private static final String TAG = "BC-SearchActivity";
    private Toolbar mToolBar;
    private SearchView mSearchView;
    private ImageView mImgBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SearchRecycleViewAdapter mRecyclerViewAdapter;

    private List<NewsInfo> infolist = new ArrayList<>();
    private int page = 1;
    private ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mImgBack = (ImageView) findViewById(R.id.back_img);
        setSupportActionBar(mToolBar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mSearchView = (SearchView) findViewById(R.id.search);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.search_result);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        configRecycleView();
        configSearchView();
    }

    private void configRecycleView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewAdapter = new SearchRecycleViewAdapter(this,infolist);
        mRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
                loadResult(mSearchView.getQuery().toString(),true);
            }
        });
    }


    private void configSearchView() {
        //设置最大宽度
        //设置是否显示搜索框展开时的提交按钮
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("搜索一下");
        mSearchView.setSubmitButtonEnabled(false);


        SearchView.SearchAutoComplete mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        //设置输入框提示文字样式
        mSearchAutoComplete.setBackground(getDrawable(R.color.main_white));
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.darker_gray));//设置提示文字颜色
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.black));//设置内容文字颜色


        ImageView AppCompatImageViewbutton=(ImageView)mSearchView.findViewById(R.id.search_mag_icon);
        LinearLayout.LayoutParams lpsearchbt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpsearchbt.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        lpsearchbt.setMargins(0,1,10 ,1);
        AppCompatImageViewbutton.setLayoutParams(lpsearchbt);
        AppCompatImageViewbutton.setPadding(0,0,0,0);

//        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
////搜索图标按钮(打开搜索框的按钮)的点击事件
//        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Open", Toast.LENGTH_SHORT).show();
//            }
//        });
//搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e(TAG, "TextSubmit : " + s);
                page = 1;
                mRecyclerView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                loadResult(s,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e(TAG, "TextChange --> " + s);
                page = 1;
                mRecyclerView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                loadResult(s,false);
               // loadResult(s);
                return false;
            }
        });


    }

    private void loadResult(String content, boolean isLoadMore) {
        if (isLoadMore) {
            Log.i(TAG,"LoadMore Log" + page);
            NetWorkMrg.requestNewsSearchList("all", page, content, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    try {
                        JSONObject responsejson = new JSONObject(response.body().string());
                        JSONArray resultArray = responsejson.optJSONArray(ConstraintUtil.RESULTS);
                        for (int i = 0; i < resultArray.length(); i++) {
                            NewsInfo info = new NewsInfo(resultArray.getJSONObject(i));
                            Log.i(TAG, "item " + i + info.getType());
                            infolist.add(info);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        }
                        page++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e(TAG, "request search error");
                }
            });
        } else {
            Log.i(TAG,"LoadMore false Log" + page);
            if (TextUtils.isEmpty(content)) {
                infolist.clear();
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                return;
            }
            NetWorkMrg.requestNewsSearchList("all", page, content, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    try {
                        JSONObject responsejson = new JSONObject(response.body().string());
                        JSONArray resultArray = responsejson.optJSONArray(ConstraintUtil.RESULTS);
                        infolist.clear();
                        for (int i = 0; i < resultArray.length(); i++) {
                            NewsInfo info = new NewsInfo(resultArray.getJSONObject(i));
                            Log.i(TAG, "item " + i + info.getType());
                            infolist.add(info);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        }
                        page++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e(TAG, "request search error");
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, WebviewActivity.class);
        NewsInfo info = infolist.get(position);
        intent.putExtra(WebviewActivity.DESC_TITLE,info.getDesc());
        intent.putExtra(WebviewActivity.URL_CONTENT, info.getUrl());
        startActivity(intent);
    }

}
