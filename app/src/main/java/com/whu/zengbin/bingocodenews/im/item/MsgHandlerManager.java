package com.whu.zengbin.bingocodenews.im.item;

import android.content.Context;
import android.util.SparseArray;
import com.whu.zengbin.bingocodenews.im.bean.MsgLocation;
import com.whu.zengbin.bingocodenews.im.bean.MsgType;

/**
 * 创建时间: 2018/10/18 17:42 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class MsgHandlerManager {
  private static final int ITEM_VIEW_TYPE_OFFSET = 2000;

  private final Context mContext;
  private final SparseArray<IMsgHandlerCreator> mMsgHandlerLeftArray = new SparseArray<>();
  private final SparseArray<IMsgHandlerCreator> mMsgHandlerMiddleArray = new SparseArray<>();
  private final SparseArray<IMsgHandlerCreator> mMsgHandlerRightArray = new SparseArray<>();

  public MsgHandlerManager(Context mContext) {
    this.mContext = mContext;
    initMsgHandlers();
  }

  private void initMsgHandlers() {
    // 文字消息（左）
    mMsgHandlerLeftArray.put(createItemViewType(MsgType.MSG_TEXT, MsgLocation.MSG_LEFT),
        new IMsgHandlerCreator() {
          @Override
          public BaseItemHandler createHandler(Context context) {
            return new TextMsgHandler(mContext, MsgLocation.MSG_LEFT);
          }
        });

    // 文字消息（中）
    mMsgHandlerLeftArray.put(createItemViewType(MsgType.MSG_TEXT, MsgLocation.MSG_MIDDLE),
        new IMsgHandlerCreator() {
          @Override
          public BaseItemHandler createHandler(Context context) {
            return new TextMsgHandler(mContext, MsgLocation.MSG_MIDDLE);
          }
        });

    // 文字消息（右）
    mMsgHandlerLeftArray.put(createItemViewType(MsgType.MSG_TEXT, MsgLocation.MSG_RIGHT),
        new IMsgHandlerCreator() {
          @Override
          public BaseItemHandler createHandler(Context context) {
            return new TextMsgHandler(mContext, MsgLocation.MSG_RIGHT);
          }
        });
  }

  public BaseItemHandler obtain(@MsgType int viewType) {
    int location = MsgLocation.MSG_LEFT;
    if (location == MsgLocation.MSG_LEFT) {
      return mMsgHandlerLeftArray.get(viewType).createHandler(mContext);
    } else if (location == MsgLocation.MSG_MIDDLE) {
      return mMsgHandlerMiddleArray.get(viewType).createHandler(mContext);
    } else {
      return mMsgHandlerRightArray.get(viewType).createHandler(mContext);
    }
  }

  public int createItemViewType(@MsgType int msgType, @MsgLocation int msgLocationType) {
    switch (msgLocationType) {
      case MsgLocation.MSG_LEFT:
        return msgType - ITEM_VIEW_TYPE_OFFSET;
      case MsgLocation.MSG_RIGHT:
        return msgType + ITEM_VIEW_TYPE_OFFSET;
      default:
        return msgType;
    }
  }
}
