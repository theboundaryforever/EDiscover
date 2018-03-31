package com.kest.ediscover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 * TODO 朋友
 */
public class FriendFragment extends Fragment {


    WebView webView;
    SharePreferenceUtil sp;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allbuy, container, false);
        /*sp = SharePreferenceUtil.getInstance(getActivity());
        webView = (WebView) view. findViewById(R.id.webview);

        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.toString().contains("baidu.com")){
                    view.loadUrl("https://www.baidu.com/");
                    return true;
                }

                return false;
            }


        });
        webView.loadUrl("http://pay.allbuy.me/webdemo");
        //webView.loadUrl("http://pay.allbuy.me/webdemo&token=" + sp.getToken());*/
        return view;
    }

}
