package com.liuy.webapp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class MainActivity extends Activity {
	private WebView mWebView;
	private Handler mHandler = new Handler();

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setContentView(R.layout.webview);
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new Contact(), "Contact");
		// mWebView.loadUrl("file:///android_asset/app/index.html");
		// mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding,
		// historyUrl);
		String data = "";
		try {
			// ��ȡassetsĿ¼�µ��ļ���Ҫ�õ�AssetManager�����Open�������ļ�
			InputStream is = getAssets().open("app/index.html");
			// loadData()������Ҫ����һ���ַ�����������������Ҫ���ļ�ת���ַ���
			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int count = 0;
			while ((count = is.read()) != -1) {
				baf.append(count);
			}
			data = EncodingUtils.getString(baf.toByteArray(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mWebView.loadData(data, "text/html", "utf-8");
	}

	private final class Contact {
		// JavaScript���ô˷�������绰
		public void call(String phone) {
			mWebView.loadUrl("javascript:wave()");
		}

		// Html���ô˷�����������Android4.2���Ժ�ע�벽�����£�
		@JavascriptInterface
		public void showcontacts() {
			Log.i("MainActivity", "����js����");
			mHandler.post(new Runnable() {
				public void run() {
					mWebView.loadUrl("javascript:wave()");
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
