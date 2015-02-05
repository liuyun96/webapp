package com.liuy.webapp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class MainActivity extends Activity {
	private WebView mWebView;
	private Handler mHandler = new Handler();

	/*
	 * @SuppressLint("SetJavaScriptEnabled") protected void onCreate(Bundle
	 * savedInstanceState) { super.onCreate(savedInstanceState);
	 * setContentView(R.layout.webview); mWebView = (WebView)
	 * findViewById(R.id.webview); WebSettings webSettings =
	 * mWebView.getSettings(); webSettings.setJavaScriptEnabled(true);
	 * mWebView.loadUrl("file:///android_asset/app/index.html"); //
	 * mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, //
	 * historyUrl);
	 * 
	 * }
	 */
	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setContentView(R.layout.webview);
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// mWebView.addJavascriptInterface(new Contact(), "Contact");
		// mWebView.loadUrl("file:///android_asset/app/index.html");
		// mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding,
		// historyUrl);

		firstMethod();
		//secondMethod();
	}

	/**
	 * 可以正常显示
	 */
	private void firstMethod() {
		mWebView.loadUrl("file:///android_asset/app/index.html");
	}

	private void secondMethod() {
		String data = "";
		try {
			// 读取assets目录下的文件需要用到AssetManager对象的Open方法打开文件
			InputStream is = getAssets().open("app/index.html");
			// loadData()方法需要的是一个字符串数据所以我们需要把文件转成字符串
			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int count = 0;
			while ((count = is.read()) != -1) {
				baf.append(count);
			}
			data = EncodingUtils.getString(baf.toByteArray(), "utf-8");
			// data = URLEncoder.encode(String.valueOf(baf.toByteArray()),
			// "utf-8");
			
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// mWebView.loadData(data, "text/html", "utf-8");

		// StringBuilder data1 = new StringBuilder(
		// "<html><body bgcolor=\"#F2F6F8\"><font color='red'>font</font>");
		// data1.append("</body></html>");
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.loadData(data, "text/html", "utf-8");
		// mWebView.loadDataWithBaseURL("about:blank", data, "text/html",
		// "utf-8", null);
	}

	private final class Contact {
		// JavaScript调用此方法拨打电话
		public void call(String phone) {
			mWebView.loadUrl("javascript:wave()");
		}

		// Html调用此方法传递数据Android4.2及以后，注入步骤如下：
		@JavascriptInterface
		public void showcontacts() {
			Log.i("MainActivity", "调用js方法");
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
