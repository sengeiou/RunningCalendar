package com.everglow.paobu.huanlepao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.everglow.paobu.huanlepao.R;

import java.io.File;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WelcomeActivity extends AppCompatActivity {

    String apkPath;
    private ProgressBar mPb;

    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mPb = findViewById(R.id.pb);
        RelativeLayout llUpdate = findViewById(R.id.ll_update);
        mWebView = findViewById(R.id.webView);
        mUrl = getIntent().getStringExtra("url");
        if (mUrl != null && !mUrl.endsWith(".apk")) {
            mWebView.setVisibility(View.VISIBLE);
            llUpdate.setVisibility(View.GONE);
            goToWeb(mUrl);
        } else {
            mWebView.setVisibility(View.GONE);
            llUpdate.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mUrl)) {
                initUpDate(mUrl);
            }
        }

    }

    public interface MyProgressListener {
        void notification(int progress, int max);
    }

    /**
     * @param downloadUrl 下载url
     */
    private void initUpDate(final String downloadUrl) {
        apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "152caizy.apk";
        DownloadManagerUtil.getInstance(this).downloadApk(apkPath, downloadUrl, new MyProgressListener() {
            @Override
            public void notification(int progress, int max) {
                mPb.setProgress(progress);
                mPb.setMax(max);
            }
        });
    }


    private void goToWeb(String url) {
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小 
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存 
        webSettings.setAllowFileAccess(true); //设置可以访问文件 
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口 
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    return false;
                }


                return true;
            }
        });
        mWebView.loadUrl(url);

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack() && mUrl != null && mUrl.endsWith(".html")) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}