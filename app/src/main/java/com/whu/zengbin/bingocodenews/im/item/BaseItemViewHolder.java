package com.whu.zengbin.bingocodenews.im.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.whu.zengbin.bingocodenews.R;

/**
 * 创建时间: 2018/10/12 18:12 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class BaseItemViewHolder {
  public View mDetailView;
  public LeftItemViewHolder mLeftItemViewHolder;
  public RightItemViewHolder mRightItemViewHolder;
  public MiddleItemViewHolder mMiddleItemViewHolder;

  public BaseItemViewHolder(View detailView) {
    mDetailView = detailView;
  }

  public class LeftItemViewHolder {
    public LinearLayout mIMItemDetailContainer;
    public ImageView mIvAvatarImage;

    public LeftItemViewHolder(View convertView) {
      mIMItemDetailContainer = (LinearLayout) convertView.findViewById(R.id.im_item_detail_container);
      mIvAvatarImage = (ImageView) convertView.findViewById(R.id.img_head);

      mIMItemDetailContainer.addView(mDetailView);
    }
  }

  public class RightItemViewHolder {
    public LinearLayout mIMItemDetailContainer;
    public ImageView mIvAvatarImage;
    public RightItemViewHolder(View convertView) {
      mIMItemDetailContainer = (LinearLayout) convertView.findViewById(R.id.im_item_detail_container);
      mIvAvatarImage = (ImageView) convertView.findViewById(R.id.img_head);

      mIMItemDetailContainer.addView(mDetailView);
    }
  }

  public class MiddleItemViewHolder {
    public MiddleItemViewHolder(View convertView) {
    }
  }

  public void initLeftViewHolder(View convertView) {
    mLeftItemViewHolder = new LeftItemViewHolder(convertView);
  }

  public void initRightViewHolder(View convertView) {
    mRightItemViewHolder = new RightItemViewHolder(convertView);
  }

  public void initMiddleViewHolder(View convertView) {
    mMiddleItemViewHolder = new MiddleItemViewHolder(convertView);
  }

}
