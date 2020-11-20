package com.example.pelatihan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.pelatihan3.Helper.BrowserHelper;
import com.google.android.material.snackbar.Snackbar;

public class WebViewAcitivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private WebView webView;
    private CoordinatorLayout coordinatorLayout;
    private String url, header;
    private float m_downX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_acitivity);
        toolbar= findViewById(R.id.toolbar);
        webView = findViewById(R.id.webView);
        progressBar= findViewById(R.id.progressBar4);
        coordinatorLayout = findViewById(R.id.main_container);
        url = getIntent().getStringExtra("url");
        header= getIntent().getStringExtra("header");
        //setup Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
        webView.loadUrl(url);


    }
    public void initWebView(){
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;
        public MyWebChromeClient(WebViewAcitivity webViewAcitivity) {
            super();
            this.context= context;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browser,menu);
        if (BrowserHelper.isBookmarked(this, webView.getUrl())) {
            BrowserHelper.colorMenuIcon(this, menu.getItem(0), R.color.colorAccent);
        }else {
            BrowserHelper.colorMenuIcon(this, menu.getItem(0), android.R.color.white);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.action_bookmark){
            BrowserHelper.bookMarkUrl(this, webView.getUrl());
            String msg = BrowserHelper.isBookmarked(this, webView.getUrl())
                    ? webView.getTitle() + "is bookmarked": webView.getTitle()+"" +
                    "removed";
            Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            snackbar.show();
            invalidateOptionsMenu();

        }
        if (item.getItemId()== android.R.id.home){
            finish();
        }
        if (item.getItemId()== R.id.action_back){
            back();
        }
        if (item.getItemId()== R.id.action_forward){
            forward();
        }
        return super.onOptionsItemSelected(item);
    }
    private void back(){
        if (webView.canGoBack()){
            webView.goBack();
        }
    }
    private void forward(){
        if (webView.canGoForward()){
            webView.goForward();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!webView.canGoBack()){
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(130);
        }else{
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(225);
        }
        if(!webView.canGoForward()) {
            menu.getItem(2).setEnabled(false);
            menu.getItem(2).getIcon().setAlpha(130);
        }else{
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).getIcon().setAlpha(225);
        }
        return true;
    }

}