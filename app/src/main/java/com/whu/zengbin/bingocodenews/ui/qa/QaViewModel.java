package com.whu.zengbin.bingocodenews.ui.qa;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class QaViewModel extends ViewModel {
  private MutableLiveData<String> myWord = new MutableLiveData<>();

  public LiveData<String> getMyWord() {
    if (myWord == null) {
      setMyWord("");
    }
    return  myWord;
  }

  public void setMyWord(String word) {
    myWord.setValue(word);
  }

}
