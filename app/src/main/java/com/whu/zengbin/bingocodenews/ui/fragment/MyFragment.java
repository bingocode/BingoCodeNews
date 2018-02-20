package com.whu.zengbin.bingocodenews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;
import com.whu.zengbin.bingocodenews.common.SnackbarUtil;
import com.whu.zengbin.bingocodenews.network.NetWorkMrg;
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
      loadMore(true);
      // 刷新时，指示器旋转后变化的颜色
      mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
      mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void configRecyclerView() {
        if (flag != ConstraintUtil.PHOTO_FLAG) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),infolist);
            mRecyclerViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
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

            }
        });
    }

    @Override
    public void onRefresh() {
        loadMore(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        //SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
        Intent intent = new Intent(getContext(), WebviewActivity.class);
        NewsInfo info = infolist.get(position);
        intent.putExtra(WebviewActivity.DESC_TITLE,info.getDesc());
        if(flag != ConstraintUtil.PHOTO_FLAG) {
            intent.putExtra(WebviewActivity.URL_CONTENT, info.getUrl());
        } else {
            int[] wh = CommonUtil.getScreenSize(getContext());
            intent.putExtra(WebviewActivity.URL_CONTENT, info.getUrl() + ConstraintUtil.IMG_SUFFIX_WIDTH + wh[0]/3);
        }
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        SnackbarUtil.show(mRecyclerView, getString(R.string.item_longclicked), 0);
    }
}
