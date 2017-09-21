package com.db.dipenrana.thenewshub.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.db.dipenrana.thenewshub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.wvArticleDetails) WebView wvArticleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        ButterKnife.bind(this);

        //hook to webeview
        SetupWebView();
    }

    private void SetupWebView() {

        wvArticleDetails.getSettings().setLoadsImagesAutomatically(true);
        wvArticleDetails.getSettings().setJavaScriptEnabled(true);
        wvArticleDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvArticleDetails.setWebViewClient(new ArticleBrowser());
        wvArticleDetails.loadUrl("http://www.example.com");
    }

    //manage url loading behaviour
    private class ArticleBrowser extends WebViewClient{

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

}

