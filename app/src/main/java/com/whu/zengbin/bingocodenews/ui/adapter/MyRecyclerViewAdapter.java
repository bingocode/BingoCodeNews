/*
 *
 *  *
 *  *  *
 *  *  *  * ===================================
 *  *  *  * Copyright (c) 2016.
 *  *  *  * 作者：安卓猴
 *  *  *  * 微博：@安卓猴
 *  *  *  * 博客：http://sunjiajia.com
 *  *  *  * Github：https://github.com/opengit
 *  *  *  *
 *  *  *  * 注意**：如果您使用或者修改该代码，请务必保留此版权信息。
 *  *  *  * ===================================
 *  *  *
 *  *  *
 *  *
 *
 */

package com.whu.zengbin.bingocodenews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bingo.greendao.gen.NewsInfoDao;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.event.NewsInfo;
import com.whu.zengbin.bingocodenews.common.CommonUtil;

import java.util.List;

/**
 * Created by Monkey on 2015/6/29.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = "BC-MyRecyclerViewAdapter";
  private static final int TYPE_ITEM = 0;
  private static final int TYPE_FOOTER = 1;
  final NewsInfoDao newsInfoDao = CodeNewsApp.getmDaoSession().getNewsInfoDao();
  private boolean isshowLoadmore = true;
  private boolean isshowcollect = true;

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  public OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public Context mContext;
  public List<NewsInfo> mDatas;
  public LayoutInflater mLayoutInflater;

  public MyRecyclerViewAdapter(Context mContext, List<NewsInfo> infolist) {
    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);
    mDatas = infolist;
  }

  public void setIsshowLoadmore(boolean isshowLoadmore) {
    this.isshowLoadmore = isshowLoadmore;
  }

  public void setIsshowcollect(boolean isshowcollect) {
    this.isshowcollect = isshowcollect;
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
      final NewsInfo info = mDatas.get(position);
      ((MyRecyclerViewHolder)holder).mTextView.setText(info.getDesc());
      ((MyRecyclerViewHolder)holder).mTimeTv.setText(info.getPublishedAt());
      String imgurl = info.getImages() + "?imageView2/0/w/180";
      RequestOptions options = new RequestOptions();
      int holderimg = CommonUtil.getHolderIcon(info.getType());
      options.centerCrop()
              .placeholder(holderimg)
              .error(holderimg)
              .fallback(holderimg);
      Glide.with(mContext)
              .load(imgurl)
              .apply(options)
              .into(((MyRecyclerViewHolder)holder).mIconImg);
      if (isshowcollect) {
        ((MyRecyclerViewHolder) holder).mSaveImg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (newsInfoDao.load(info.getId()) == null) {
              ((MyRecyclerViewHolder) holder).mSaveImg.setImageResource(R.mipmap.icon_save_finish);
              newsInfoDao.insert(info);
              Toast.makeText(mContext, "收藏成功！", Toast.LENGTH_SHORT).show();
            } else {
              ((MyRecyclerViewHolder) holder).mSaveImg.setImageResource(R.mipmap.icon_save);
              newsInfoDao.delete(info);
              Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
            }
          }
        });
        ((MyRecyclerViewHolder) holder).mSaveImg.setImageResource(newsInfoDao.load(info.getId()) == null ? R.mipmap.icon_save : R.mipmap.icon_save_finish);
      } else {
        ((MyRecyclerViewHolder) holder).mSaveImg.setVisibility(View.GONE);
      }

    } else if (holder instanceof FootViewHolder) {
      if(!isshowLoadmore) {
        holder.itemView.setVisibility(View.GONE);
      } else {
        holder.itemView.setVisibility(View.VISIBLE);
      }
    }
  }

  @Override
  public int getItemCount() {
    return mDatas.size() == 0 ? 1 : mDatas.size() + 1;
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
