package com.whu.zengbin.bingocodenews.im;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.item.BaseItemHandler;
import com.whu.zengbin.bingocodenews.im.item.MsgHandlerManager;
import com.whu.zengbin.bingocodenews.im.tool.IMHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2018/10/12 16:37 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class IMListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  List<Msg> mMsgs = new ArrayList<>();
  Context mContext;
  MsgHandlerManager mMsgHandlerManager;

  public void setDatas(@Nullable List<Msg> Msgs) {
    mMsgs.clear();
    if (Msgs != null && Msgs.size() !=0) {
      mMsgs.addAll(Msgs);
    }
    this.notifyDataSetChanged();
  }

  public List<Msg> getDatas() {
    return mMsgs;
  }

  public Msg getItem(int position) {
    return mMsgs.get(position);
  }

  public void add(Msg Msg) {
    mMsgs.add(Msg);
    notifyItemInserted(mMsgs.size() - 1);
  }

  public IMListAdapter(Context context) {
    this.mContext = context;
    mMsgHandlerManager = new MsgHandlerManager(context);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    BaseItemHandler itemHandler = mMsgHandlerManager.obtain(viewType);
    return new IMViewHolder(itemHandler, parent);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    IMViewHolder viewHolder = (IMViewHolder) holder;
    viewHolder.mItemHandler.bindView(holder.itemView, getItem(position), position);
  }

  @Override
  public int getItemCount() {
    return mMsgs.size();
  }

  @Override
  public int getItemViewType(int position) {
    Msg msg = getItem(position);
    return mMsgHandlerManager.createItemViewType(msg.msgType, IMHelper.getMsgLocation(msg));
  }

  static class IMViewHolder extends RecyclerView.ViewHolder{
    final BaseItemHandler mItemHandler;

    public IMViewHolder(BaseItemHandler itemHandler, ViewGroup parent) {
      super(itemHandler.getView(parent));
      mItemHandler = itemHandler;
    }
  }
}
