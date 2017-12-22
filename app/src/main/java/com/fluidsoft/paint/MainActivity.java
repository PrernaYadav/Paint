package com.fluidsoft.paint;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    String url = "http://paintonline.in";
    boolean loadingFinished = true;
    boolean redirect = false;
    final Handler handler = new Handler();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
                handler.postDelayed(this, 2000);
            }
        };

//Start
        handler.postDelayed(runnable, 2000);



        WebViewClient yourWebClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(url);
            }
        };
        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(false);

/*
        String userAgent = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3";
        webView.getSettings().setUserAgentString(userAgent);*/


        webView.loadUrl(url);
        webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");



        webView.setWebViewClient(new WebViewClient(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
               /* pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("loading");
                pd.show();*/
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!redirect){
                    loadingFinished = true;
                }

                if(loadingFinished && !redirect){
//                    pd.dismiss();
                    //HIDE LOADING IT HAS FINISHED
                } else{
                    redirect = false;
                }
            }
        });



    }
    }

