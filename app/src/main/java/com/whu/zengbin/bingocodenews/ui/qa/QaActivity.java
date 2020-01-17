package com.whu.zengbin.bingocodenews.ui.qa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.ui.qa.QaFragment;

public class QaActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.qa_activity);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, QaFragment.newInstance())
          .commitNow();
    }
  }
}
