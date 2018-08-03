package com.whu.zengbin.bingocodenews.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by zengbin on 2018/3/13.
 */

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private static final String TAG = "BC-VideoRecyclerViewAdapter";
private static final int TYPE_ITEM = 0;
private static final int TYPE_FOOTER = 1;
final NewsInfoDao newsInfoDao = CodeNewsApp.getmDaoSession().getNewsInfoDao();
private boolean isshowLoadmore = true;
private boolean isshowcollect = true;

public interface OnItemClickListener {
    void onItemClick(View view, int position);
}

    public VideoRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(VideoRecyclerViewAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public Context mContext;
    public List<NewsInfo> mDatas;
    public LayoutInflater mLayoutInflater;

    public VideoRecyclerViewAdapter(Context mContext, List<NewsInfo> infolist) {
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
            View mView = mLayoutInflater.inflate(R.layout.item_video, parent, false);
            VideoViewHolder mViewHolder = new VideoViewHolder(mView);
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
        if (holder instanceof VideoViewHolder) {
            if (mOnItemClickListener != null) {
                ((VideoViewHolder)holder).info_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
            final NewsInfo info = mDatas.get(position);
            Log.i(TAG, "info id:" + info.getId() +"info desc:" + info.getDesc());
            ((VideoViewHolder)holder).mTextView.setText(info.getDesc());
            ((VideoViewHolder)holder).mTimeTv.setText(info.getPublishedAt());
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
                    .into(((VideoViewHolder)holder).mIconImg);
            if (isshowcollect) {
                ((VideoViewHolder) holder).mSaveImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (newsInfoDao.load(info.getId()) == null) {
                            ((VideoViewHolder) holder).mSaveImg.setImageResource(R.mipmap.icon_save_finish);
                            newsInfoDao.insert(info);
                            Toast.makeText(mContext, "收藏成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            ((VideoViewHolder) holder).mSaveImg.setImageResource(R.mipmap.icon_save);
                            newsInfoDao.delete(info);
                            Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ((VideoViewHolder) holder).mSaveImg.setImageResource(newsInfoDao.load(info.getId()) == null ? R.mipmap.icon_save : R.mipmap.icon_save_finish);
            } else {
                ((VideoViewHolder) holder).mSaveImg.setVisibility(View.GONE);
            }

            ((VideoViewHolder) holder).videoplayer.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
            ((VideoViewHolder) holder).videoplayer.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));


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

