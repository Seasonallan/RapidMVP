package com.season.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

/**
 * 客服 网页
 */
public class WebActivity extends BaseTLEActivity {

    WebView mWebView;


    public static void open(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getTitleBar().setTopTile("预览网页");
        getTitleBar().enableLeftButton();

        mWebView = findViewById(R.id.customer_wv);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            try {
                mWebView.clearHistory();
                mWebView.clearCache(true);
                mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
                mWebView.freeMemory();
                mWebView.pauseTimers();
            } catch (Exception e) {
            }
            mWebView = null;
        }
    }


}
