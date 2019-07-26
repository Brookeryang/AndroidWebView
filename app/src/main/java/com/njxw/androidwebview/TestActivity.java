package com.njxw.androidwebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author yyh
 * <p>
 * 时间：2019-07-26 15
 * <p>
 * QQ：1355857303
 * <p>
 * 描述:
 */
public class TestActivity extends AppCompatActivity {
    private WebView webView;
    private EditText editText;
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        webView = findViewById(R.id.web_view);
        editText = findViewById(R.id.edit_content);
        webView.setVerticalScrollbarOverlay(true);
        //设置webView 允许加载js代码
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        //在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }

    //在java中调用js代码
    public void sendInfoToJs(View view) {
        String msg = editText.getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }


    public class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }
}
