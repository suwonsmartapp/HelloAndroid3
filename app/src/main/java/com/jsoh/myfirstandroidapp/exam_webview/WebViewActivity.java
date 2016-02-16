
package com.jsoh.myfirstandroidapp.exam_webview;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditAddress;
    private Button mBtnAccess;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mEditAddress = (EditText) findViewById(R.id.address_edit);
        mBtnAccess = (Button) findViewById(R.id.access_btn);
        mWebView = (WebView) findViewById(R.id.webview);

        mBtnAccess.setOnClickListener(this);

        // 요것을 설정하지 않으면 다른 웹브라우저가 뜸
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onClick(View v) {
        mWebView.loadUrl(mEditAddress.getText().toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
