package com.whu.zengbin.bingocodenews.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.common.JsonUtil;
import com.whu.zengbin.bingocodenews.common.LogUtil;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgListResult;
import com.whu.zengbin.bingocodenews.im.biz.ITalk;
import com.whu.zengbin.bingocodenews.im.biz.impl.TalkPresenter;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

/**
 * 创建时间: 2018/10/12 14:52 <br>
 * 作者: zengbin <br>
 * 描述: 聊天页面
 */
public class IMFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IMInputLayout.InputCallBack {
  private static final String TAG = "IMFragment";
  private LinearLayoutManager mLinearLayoutManager;

  SwipeRefreshLayout mSwipeRefreshLayout;
  RecyclerView mIMList;
  IMInputLayout.IMInputViewHolder mIMInputViewHolder;
  IMListAdapter mAdapter;
  private View mView;
  ITalk.ITalkPresenter mPresenter;

  private ITalk.ITalkView mTalkView = new ITalk.ITalkView() {
    @Override
    public void setPresenter(ITalk.ITalkPresenter presenter) {
      mPresenter = presenter;
      if (mPresenter == null) {
        getActivity().finish();
        return;
      }
    }

    @Override
    public void onRefreshComplete() {
      mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addMsgs(List<Msg> msgs) {
      mAdapter.addDatas(msgs);
    }
  };

  public IMFragment() {

  }

  public static IMFragment newInstance(Bundle bundle) {
    IMFragment fragment = new IMFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_im_layout, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mView = view;
    initView();
    mPresenter.enterIM();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onDestroyView() {
    mPresenter.quitIM();
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }

  private void initView() {
    mSwipeRefreshLayout = mView.findViewById(R.id.im_swipe_refresh_layout);
    mIMList = mView.findViewById(R.id.im_list);
    mIMInputViewHolder = new IMInputLayout.IMInputViewHolder(mView.findViewById(R.id.im_input_layout));
    mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    IMInputLayout.initInputLayout(mIMInputViewHolder, this);
    mTalkView.setPresenter(new TalkPresenter(mTalkView));
    initRecyclerView();
  }

  private void initRecyclerView() {
    mLinearLayoutManager = new LinearLayoutManager(getContext());
    mLinearLayoutManager.setReverseLayout(true);
    mLinearLayoutManager.setStackFromEnd(true);
    mAdapter = new IMListAdapter(this.getActivity());
    mIMList.setLayoutManager(mLinearLayoutManager);
    mIMList.setAdapter(mAdapter);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void receiveMsg(Msg msg) { // 收到消息
    LogUtil.i(TAG, "receiveMsg" + msg.msgContent);
    mAdapter.add(msg);
  }

  @Override
  public void onSendClick(Msg msg) {
    mPresenter.sendIMMsg(msg);
  }

  @Override
  public void onRefresh() {
    mPresenter.fetchIMMsgs();
  }
}
