package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.andela.buildsdgs.rtrc.utility.WebViewClientImpl;

public class PaymentWebActivity extends AppCompatActivity {

    private WebView webView = null;
    private Tools serviceTools = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top Up Wallet");
        webView = findViewById(R.id.payment_webview);
        serviceTools =  new Tools(PaymentWebActivity.this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("https://toll-revenue.digital/payments/"+serviceTools.retrieveUserProfile().getToken()+"/"+serviceTools.retrieveUserProfile().getUser().getEmail());
        //toll-revenue.digital/payments/token/email

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
