package com.whu.zengbin.bingocodenews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;
import com.whu.zengbin.bingocodenews.ui.activity.SearchActivity;

import java.util.List;

/**
 * Created by zengbin on 2018/2/17.
 */

public class SearchRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "BC-SearchRecycleViewAdapter";
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public SearchRecycleViewAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(SearchRecycleViewAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public Context mContext;
    public List<NewsInfo> mDatas;
    public LayoutInflater mLayoutInflater;

    public SearchRecycleViewAdapter(Context mContext, List<NewsInfo> infolist) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = infolist;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
            MyRecyclerViewHolder mViewHolder = new MyRecyclerViewHolder(mView);
            return mViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_view, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyRecyclerViewHolder) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
            ((MyRecyclerViewHolder)holder).mTextView.setText(mDatas.get(position).getDesc());
            ((MyRecyclerViewHolder)holder).mTimeTv.setText(mDatas.get(position).getPublishedAt());
            String imgurl = mDatas.get(position).getImages() + "?imageView2/0/w/180";
            RequestOptions options = new RequestOptions();
            int holderimg = CommonUtil.getHolderIcon(mDatas.get(position).getType());
            options.centerCrop()
                    .placeholder(holderimg)
                    .error(holderimg)
                    .fallback(holderimg);
            Glide.with(mContext)
                    .load(imgurl)
                    .apply(options)
                    .into(((MyRecyclerViewHolder)holder).mIconImg);
        } else if (holder instanceof FootViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
}

