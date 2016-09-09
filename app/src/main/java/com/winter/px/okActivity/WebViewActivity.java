package com.winter.px.okActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.winter.px.niceview.R;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        initView();
        Intent intent = getIntent();
        String web_url = intent.getStringExtra("web_url");
        Log.d("5555555555",""+web_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(web_url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webActivityId);
        progressBar = (ProgressBar) findViewById(R.id.progressBarId);
    }

    public void onClick(View view) {
        finish();
    }
}
