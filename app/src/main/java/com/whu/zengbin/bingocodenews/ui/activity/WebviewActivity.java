package com.whu.zengbin.bingocodenews.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whu.zengbin.bingocodenews.R;

public class WebviewActivity extends AppCompatActivity {
    public static final String DESC_TITLE = "desc_title";
    public static final String URL_CONTENT = "url_content";
    public static final String TAG = "BC-WebviewActivity";
    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mWebView = (WebView) findViewById(R.id.webView);
        configview();
    }

    private void configview() {
        Intent intent = getIntent();
        mToolbar.setTitle(intent.getStringExtra(DESC_TITLE));
        setSupportActionBar(mToolbar);
        configWebView(intent.getStringExtra(URL_CONTENT));
    }
    private void configWebView(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }

}
