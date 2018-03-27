package com.whu.zengbin.bingocodenews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;
import com.whu.zengbin.bingocodenews.common.SnackbarUtil;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
import com.whu.zengbin.bingocodenews.ui.activity.ImagesActivity;
import com.whu.zengbin.bingocodenews.ui.activity.WebviewActivity;
import com.whu.zengbin.bingocodenews.ui.adapter.MyRecyclerViewAdapter;
import com.whu.zengbin.bingocodenews.ui.adapter.MyStaggeredViewAdapter;

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

public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.OnItemClickListener, MyStaggeredViewAdapter.OnItemClickListener {
    private static final String TAG = "BC-MyFragment";
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private MyStaggeredViewAdapter mStaggeredAdapter;
    private static final int SPAN_COUNT = 2;
    private int flag = 0;
    private int page = 1;
    private List<NewsInfo> infolist = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      mView = inflater.inflate(R.layout.frag_main, container, false);
      return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.id_swiperefreshlayout);
      mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerview);

      flag = (int) getArguments().get("flag");
      configRecyclerView();
      // 刷新时，指示器旋转后变化的颜色
      mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
      mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (page == 1) {
            infolist.clear();
            loadMore(true);
        }
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void configRecyclerView() {
        if (flag != ConstraintUtil.PHOTO_FLAG) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),infolist);
            mRecyclerViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerViewAdapter.setIsshowLoadmore(false);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == mRecyclerViewAdapter.getItemCount()) {
                        if (!isLoading) {
                            isLoading = true;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadMore(true);
                                    mRecyclerViewAdapter.setIsshowLoadmore(true);
                                }
                            }, 1000);
                        }
                    }
                }
            });

        } else {
            mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
            mStaggeredAdapter = new MyStaggeredViewAdapter(getActivity(),infolist);
          mStaggeredAdapter.setOnItemClickListener(this);
          mRecyclerView.setAdapter(mStaggeredAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);

        }
    private void loadMore(final boolean isloadmore){
        NetWorkMrg.requestNewsInfo(CommonUtil.flagConvertStr(flag), page, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                try {
                    JSONObject responsejson = new JSONObject(response.body().string());
                    JSONArray resultArray = responsejson.optJSONArray(ConstraintUtil.RESULTS);
                    for(int i = 0; i < resultArray.length(); i++){
                        NewsInfo info = new NewsInfo(resultArray.getJSONObject(i));
                        if (isloadmore) {
                            infolist.add(info);
                        } else {
                            infolist.add(0,info);
                        }
                        if (flag != ConstraintUtil.PHOTO_FLAG) {
                            isLoading = false;
                            mRecyclerViewAdapter.setIsshowLoadmore(false);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            mStaggeredAdapter.notifyDataSetChanged();
                        }
                    }
                    page ++;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadMore(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        NewsInfo info = infolist.get(position);
        Intent intent;
        if(flag != ConstraintUtil.PHOTO_FLAG) {
            intent = new Intent(getContext(), WebviewActivity.class);
            intent.putExtra(WebviewActivity.DESC_TITLE,info.getDesc());
            intent.putExtra(WebviewActivity.URL_CONTENT, info.getUrl());
        } else {
            intent = new Intent(getContext(), ImagesActivity.class);
            intent.putExtra(ImagesActivity.URL_IMG,info.getUrl());
            intent.putExtra(ImagesActivity.CONTENT_TITLE,info.getDesc());
        }
        getActivity().startActivity(intent);
    }
}
