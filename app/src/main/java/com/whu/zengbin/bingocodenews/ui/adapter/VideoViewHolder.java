package com.whu.zengbin.bingocodenews.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whu.zengbin.bingocodenews.R;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by zengbin on 2018/3/13.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;
    public ImageView mIconImg;
    public TextView mTimeTv;
    public CardView mCardview;
    public ImageView mSaveImg;
    public RelativeLayout info_rl;
    public JZVideoPlayerStandard videoplayer;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.id_textview);
        mIconImg = (ImageView) itemView.findViewById(R.id.icon_catagory);
        mTimeTv = (TextView) itemView.findViewById(R.id.tv_time);
        mCardview = (CardView) itemView.findViewById(R.id.id_cardview);
        mSaveImg = (ImageView) itemView.findViewById(R.id.img_save);
        info_rl = (RelativeLayout) itemView.findViewById(R.id.info_rl);
        videoplayer = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
    }
}

