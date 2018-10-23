package com.whu.zengbin.bingocodenews.im.item;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgLocation;

/**
 * 创建时间: 2018/10/12 17:50 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public abstract class BaseItemHandler implements ItemHandler {
  protected final LayoutInflater mInflater;
  protected final Context mContext;

  protected Msg mMsg;
  protected int mPosition;
  protected BaseItemViewHolder mBaseViewHolder;
  protected boolean mIsMe;
  protected final int mMsgLocation;


  public BaseItemHandler(Context context, @MsgLocation int msgLocation) {
    mContext = context;
    mInflater = LayoutInflater.from(context);
    mMsgLocation = msgLocation;
    mIsMe = (msgLocation == MsgLocation.MSG_RIGHT);
  }

  @Override public void bindView(@NonNull View convertView, @NonNull Msg msg, int position) {
    mBaseViewHolder = (BaseItemViewHolder) convertView.getTag(R.id.im_view_holder);
    mMsg = msg;
    mPosition = position;
    switch (mMsgLocation) {
      case MsgLocation.MSG_LEFT:
        bindLeftView(mBaseViewHolder.mLeftItemViewHolder, msg, position);
        break;
      case MsgLocation.MSG_MIDDLE:
        bindMiddleView(mBaseViewHolder.mMiddleItemViewHolder, msg, position);
        break;
      case MsgLocation.MSG_RIGHT:
        bindRightView(mBaseViewHolder.mRightItemViewHolder, msg, position);
        break;
    }
    setupMsgClickListener(mBaseViewHolder.mDetailView, msg);
    bindViewData(convertView, msg, position);
  }

  @Override
  public View getView(ViewGroup parent) {
    View convertView;
    View detailView = mInflater.inflate(detailLayoutResId(), parent, false);
    BaseItemViewHolder viewHolder = new BaseItemViewHolder(detailView);
    switch (mMsgLocation) {
      case MsgLocation.MSG_LEFT:
        convertView = mInflater.inflate(R.layout.item_im_main_left_layout, parent, false);
        viewHolder.initLeftViewHolder(convertView);
        break;
      case MsgLocation.MSG_MIDDLE:
        convertView = mInflater.inflate(R.layout.item_im_main_middle_layout, parent, false);
        viewHolder.initMiddleViewHolder(convertView);
        break;
      case MsgLocation.MSG_RIGHT:
        convertView = mInflater.inflate(R.layout.item_im_main_right_layout, parent, false);
        viewHolder.initRightViewHolder(convertView);
        break;
      default:
        convertView = detailView;
        break;
    }
    convertView.setTag(R.id.im_view_holder, viewHolder);
    return convertView;
  }

  // 初始化消息内容
  protected abstract void bindViewData(@NonNull View convertView, @NonNull Msg msg, int position);

  @LayoutRes
  protected abstract int detailLayoutResId();

  // 设置消息点击事件
  public void setupMsgClickListener(View detailView, final Msg msg) {

  }

  public void bindLeftView(BaseItemViewHolder.LeftItemViewHolder viewHolder, Msg msg, int position) {
      // 设置头像等外在数据
  }
  public void bindMiddleView(BaseItemViewHolder.MiddleItemViewHolder viewHolder, Msg msg, int position) {

  }

  public void bindRightView(BaseItemViewHolder.RightItemViewHolder viewHolder, Msg msg, int position) {

  }
}
