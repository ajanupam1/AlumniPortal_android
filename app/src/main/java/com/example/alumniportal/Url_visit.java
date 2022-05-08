package com.example.alumniportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Url_visit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_visit);



        // full screen
//
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //Web view
        WebView v = findViewById(R.id.webview);
        v.getSettings().setJavaScriptEnabled(true);
        v.setWebViewClient(new WebViewClient());
        v.loadUrl(url);
    }
}