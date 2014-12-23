package com.liuy.webapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends BaseActivity
{
    private WebView myWebView = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_web);

        // ����ҳ
        myWebView = (WebView) findViewById(R.id.webview);
        //

        // myWebView.loadUrl("http://www.cnblogs.com/mengdd/");// ��������
        myWebView.loadUrl("http://www.baidu.com/");// �ٶ�����

        // JavaScriptʹ��(���Ҫ���ص�ҳ������JS���룬�����ʹ��JS)
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // ��WebView�д����ӣ�Ĭ����Ϊ��ʹ������������ô������WebView�򿪣�
        // myWebView.setWebViewClient(new WebViewClient());
        // �������ú����е����Ӷ����ڵ�ǰWebView�д�

        // ��ǿ�Ĵ����ӿ��ƣ��Լ���дһ��WebViewClient�ࣺ����ָ�����Ӵ�WebView�򿪣�����������Ĭ�ϴ�
        myWebView.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.activity_web, menu);
        return true;
    }

    /**
     * �Զ����WebViewClient�࣬���������Ӵ�WebView�򿪣�����������Ȼ��Ĭ���������
     * 
     * @author 1
     * 
     */
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (Uri.parse(url)
                    .getHost()
                    .equals("http://www.cnblogs.com/mengdd/archive/2013/02/27/2935811.html")
                    || Uri.parse(url).getHost()
                            .equals("http://music.baidu.com/"))
            {
                // This is my web site, so do not override; let my WebView load
                // the page

                // ���ǹ����ϵ����ӣ������ҵ���ض����ӵ�ʱ����Ȼ������������������Լ���WebView�򿪣������������view.loadUrl(url)��Ȼ������������޽⣬��֪�������������
                // view.loadUrl(url);
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch
            // another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    /**
     * ������Ӧ����WebView�в鿴��ҳʱ�������ؼ���ʱ�������ʷ�˻�,������������������WebView�����˳�
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack())
        {
            // ���ؼ��˻�
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}