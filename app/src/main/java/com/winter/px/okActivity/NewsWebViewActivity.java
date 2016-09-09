package com.winter.px.okActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
public class NewsWebViewActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webView;
    ProgressBar progressBar;
Button backButton,shareButton,favButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
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
        webView = (WebView) findViewById(R.id.newsWebActivityId);
        progressBar = (ProgressBar) findViewById(R.id.newsProgressBarId);
        backButton = (Button) findViewById(R.id.newsBackId);
        backButton.setOnClickListener(this);
        shareButton = (Button) findViewById(R.id.newsShareId);
        shareButton.setOnClickListener(this);
        favButton = (Button) findViewById(R.id.newsFaverId);
        favButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newsBackId:
                finish();
                break;
            case R.id.newsShareId:

                break;
            case R.id.newsFaverId:

                break;

        }
    }
}
