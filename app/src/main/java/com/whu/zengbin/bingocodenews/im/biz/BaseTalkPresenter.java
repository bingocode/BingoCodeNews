package com.whu.zengbin.bingocodenews.im.biz;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 创建时间: 2018/10/24 11:37 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public abstract class BaseTalkPresenter<T> implements ITalk.ITalkPresenter{
    protected Reference<T> mView;

    public void attachView(T view) {
      mView = new WeakReference<T>(view);
    }

    protected T getView() {
      return mView.get();
    }

    public boolean isViewAttached() {
      return mView != null && mView.get() != null;
    }

    public void detachView() {
      if(mView != null) {
        mView.clear();
        mView = null;
      }
    }

  }
