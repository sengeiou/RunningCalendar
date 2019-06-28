package com.everglow.paodekuaijibu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/web/ActivityWeb2")
public class ActivityWeb2 extends BaseWebView {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTitle.setText("体育资讯");
    }

    @Override
    public void requestWeb() {
        mWebView.loadUrl(Api.UnlockFragmentUrl2);
        
       
    }

    @Override
    public void hideBottom() {
        try {
            //定义javaScript方法
            String javascript = "javascript:(function() { " +
//                    "document.getElementsByTagName('logo')[0].style.display='none'; " +
                    "document.getElementsByClassName('site-top clearfix')[0].style.display='none'; " +
                    "document.getElementsByClassName('site-titan24')[0].style.display='none'; " +

                    "})()";


            //加载方法
            mWebView.loadUrl(javascript);
            //执行方法
            mWebView.loadUrl("javascript:hideBottom();");

//            String javascript1 = "javascript:(function() { " +
//                    "document.getElementsByClassName('other-login')[0].style.display='none'; " +
//                    "})()";
////            //加载方法
//            mWebView.loadUrl(javascript1);
//            //执行方法
//            mWebView.loadUrl("javascript:hideBottom();");
//         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
