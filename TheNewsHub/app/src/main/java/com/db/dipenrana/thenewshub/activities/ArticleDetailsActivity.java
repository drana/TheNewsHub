package com.db.dipenrana.thenewshub.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.db.dipenrana.thenewshub.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.wvArticleDetails) WebView wvArticleDetails;
    @BindView(R.id.toolbarDetails) Toolbar toolbar;
    String webURL;
    private ShareActionProvider miShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Intent intent = getIntent();
        webURL = intent.getStringExtra("ARTICLE_WEB_URL");

        ButterKnife.bind(this);

        //set toolbar as actionbar for this activity
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //hook to webeview
        SetupWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_details_activity,menu);
        // Fetch reference to the share action provider
        //miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();

        if(item.getItemId() == R.id.miShare){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, webURL);
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
        }
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return  true;
    }

    private void SetupWebView() {

        wvArticleDetails.getSettings().setLoadsImagesAutomatically(true);
        wvArticleDetails.getSettings().setJavaScriptEnabled(true);
        wvArticleDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvArticleDetails.setWebViewClient(new ArticleBrowser());
        // Enable responsive layout
        wvArticleDetails.getSettings().setUseWideViewPort(true);
        wvArticleDetails.loadUrl(webURL);
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

