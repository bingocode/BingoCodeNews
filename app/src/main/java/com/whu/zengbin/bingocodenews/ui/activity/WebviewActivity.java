package com.whu.zengbin.bingocodenews.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.whu.zengbin.bingocodenews.BaseActivity;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.common.CommonUtil;

public class WebviewActivity extends BaseActivity {
    public static final String DESC_TITLE = "desc_title";
    public static final String URL_CONTENT = "url_content";

    public static final String TAG = "BC-WebviewActivity";
    private Toolbar mToolbar;
    private WebView mWebView;
    private ProgressBar mProgressbar;
    private RelativeLayout loadfailed_ll;
    private String title;
    private String url;
    private boolean isloadFaild = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBar);
        loadfailed_ll = (RelativeLayout) findViewById(R.id.loadfailed_ll);
        Intent intent = getIntent();
        title = intent.getStringExtra(DESC_TITLE);
        url = intent.getStringExtra(URL_CONTENT);
        configview();
    }

    private void configview() {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configWebView();
    }
    private void configWebView() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        if (CommonUtil.isNetworkAvailable(this)) {
//            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
//            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setVerticalScrollbarOverlay(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "load start");

                loadfailed_ll.setVisibility(View.GONE);
                mProgressbar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                isloadFaild = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "load finished");
                if (!isloadFaild) {
                    Log.i(TAG, "load finished");
                    loadfailed_ll.setVisibility(View.GONE);
                    mProgressbar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i(TAG, "load error");

                loadfailed_ll.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                isloadFaild = true;
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
