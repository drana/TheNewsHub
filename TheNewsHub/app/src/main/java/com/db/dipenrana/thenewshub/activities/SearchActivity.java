package com.db.dipenrana.thenewshub.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.adapters.ArticleRecyclerViewAdapter;
import com.db.dipenrana.thenewshub.fragments.FilterFragment;
import com.db.dipenrana.thenewshub.models.Article;
import com.db.dipenrana.thenewshub.models.ArticleFilter;
import com.db.dipenrana.thenewshub.utils.EndlessRecyclerViewScrollListener;
import com.db.dipenrana.thenewshub.utils.ItemClickSupport;
import com.db.dipenrana.thenewshub.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements FilterFragment.FiltersDialogListener {

    @BindView(R.id.rvResults) RecyclerView rvQueryResults;
    @BindView(R.id.toolbar) Toolbar toolbar;

    //instance of model
    ArrayList<Article> articles = new ArrayList<Article>();
    String queryURL;
    String searchQuery;

    //define adapter and recycle view
    ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    RecyclerView rvArticleItems;
    MenuItem searchItem;
    SearchView searchView;
    EndlessRecyclerViewScrollListener scrollListener;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    //article page info
    int articleHits;
    int articleOffset;
    // Article filter
    ArticleFilter appliedFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //bind view
        ButterKnife.bind(this);

        //set toolbar as actionbar for this activity
        setSupportActionBar(toolbar);

        //instance of adapter
        articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this,articles);
        rvArticleItems = findViewById(R.id.rvResults);
        rvArticleItems.setHasFixedSize(true);

        //attach adapter to view
        rvArticleItems.setAdapter(articleRecyclerViewAdapter);

        //setup staggeredGridLayout
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rvArticleItems.setLayoutManager(staggeredGridLayoutManager);

        //on an article click event
        SetupListViewCLickListener();

        //Setup scrolllistner
        SetupScrollListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_activity,menu);

        SearchManager searchManager = (SearchManager)getSystemService(this.SEARCH_SERVICE);
        searchItem = menu.findItem(R.id.miSearch);
        searchView = (SearchView) searchItem.getActionView();
        hideSoftKeyboard(searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("enter keyword");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    ResetLayout();
                    searchQuery = query;
                    //first query should have page 0
                    FetchNewArticles(query,0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hideSoftKeyboard(searchView);
                searchView.setQuery("",false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.getItemId();

        if(item.getItemId() == R.id.miFilter){
            FragmentManager fm = getSupportFragmentManager();
            FilterFragment filterFragment = FilterFragment.newInstance("Hello","World");
            filterFragment.show(fm, "fragFilter");
        }
        return  true;
    }

    //attach listeners for article items
    private void SetupListViewCLickListener() {

        ItemClickSupport.addTo(rvArticleItems).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // do it
                        // first parameter is the context, second is the class of the activity to launch
                        Article details = articles.get(position);
                        Intent intent = new Intent(SearchActivity.this,ArticleDetailsActivity.class);
                        intent.putExtra("ARTICLE_WEB_URL", details.getWebUrl());
                        //intent.putExtra("Genres", (Parcelable) details.getGenreList());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }
        );
    }

    //attach endless scroll listeners
    private void SetupScrollListener() {
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                try{
                    loadNextDataFromApi(page);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        // Adds the scroll listener to RecyclerView
        rvArticleItems.addOnScrollListener(scrollListener);

    }

    //setup data for endless scroll listeners
    private void loadNextDataFromApi(int page) throws IOException {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        if(articleOffset<articleHits) {
            FetchNewArticles(searchQuery, page);
            Log.d("Pages added",Integer.toString(page));
        }
        else
            Toast.makeText(this, "All pages found Please try another search", Toast.LENGTH_SHORT).show();

    }

    //setup okhttp client and fetch articles from nyt api
    private void FetchNewArticles(String query4client, int page) throws IOException {

        // filter url
        queryURL = getQueryURL(query4client,page,appliedFilters);

        // setup network client for search requests
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(queryURL)
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("response","Got response");
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(response.body().string());
                    JSONArray resultsArray = jsonResponse.getJSONObject("response").getJSONArray("docs");
                    JSONObject pageInfo =  jsonResponse.getJSONObject("response").getJSONObject("meta");
                    articleHits = pageInfo.getInt("hits");
                    articleOffset = pageInfo.getInt("offset");

                    articles.addAll(Article.parseJsonArray(resultsArray.toString()));
                    SearchActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            articleRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                    Log.d("response","notify articles dataset changed");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("response","Failed to get array list of articles");
                }
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });

        Log.d("Connect", "Got response from NYT");

    }

    //clear layout before another search
    private void ResetLayout() {
        //clear search query
        searchQuery="";
        // 1. First, clear the array of data
        articles.clear();
        // 2. Notify the adapter of the update
        articleRecyclerViewAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
    }

    //hide keyboard after search enter
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //build url for search query
    public String getQueryURL(String query,int page,ArticleFilter newsFilter){

        //build url with params
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CommonUtils.API_URL).newBuilder();
        urlBuilder.addQueryParameter("api-key", "3ae9d158e4744dfb85debb2906d27b77");
        urlBuilder.addQueryParameter("q", query);
        //check if filter applied
        if(newsFilter != null){
            //sort order
            urlBuilder.addQueryParameter("sort",newsFilter.getSortSelection().toString());
            //news section
            int newsDesksSize = newsFilter.getCbNewsSection().size();
            String stringNewsDesk="";
            StringBuilder newsDeskBuilder = new StringBuilder();
            if( newsDesksSize>0){
                newsDeskBuilder.append(CommonUtils.NEWSDESK);
                for(int i=0;i< newsDesksSize;i++){
                    newsDeskBuilder.append(newsFilter.getCbNewsSection().get(i));
                    newsDeskBuilder.append(CommonUtils.WHITESPACE);
                }
                newsDeskBuilder.append(")");
                urlBuilder.addQueryParameter("fq",newsDeskBuilder.toString());
            }
            Log.d("newsdesk",newsDeskBuilder.toString());
        }
        if(page >0)
        {
            urlBuilder.addQueryParameter("page",Integer.toString(page));
        }
        return (urlBuilder.build().toString());
    }

    @Override
    public void onApplyArticleFilters(ArticleFilter articleFilter){
        appliedFilters = articleFilter;
    Log.d("filters",articleFilter.getSelectedDate());
    }

}
