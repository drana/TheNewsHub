package com.db.dipenrana.thenewshub.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.adapters.ArticleRecyclerViewAdapter;
import com.db.dipenrana.thenewshub.models.Article;
import com.db.dipenrana.thenewshub.utils.ItemClickSupport;
import com.db.dipenrana.thenewshub.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.onClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.etQuery) EditText etSearchQuery;
    @BindView(R.id.btnSearch) Button btnSearchQuery;
    @BindView(R.id.rvResults) RecyclerView rvQueryResults;
    @BindView(R.id.toolbar) Toolbar toolbar;

    //instance of model
    ArrayList<Article> articles = new ArrayList<Article>();

    //define adapter and recycle view
    ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    RecyclerView rvArticleItems;
    MenuItem searchItem;

    //staggered view
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


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

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rvArticleItems.setLayoutManager(gaggeredGridLayoutManager);


        //rvArticleItems.setLayoutManager(new LinearLayoutManager(this));

        SetupListViewCLickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_activity,menu);


        SearchManager searchManager = (SearchManager)getSystemService(this.SEARCH_SERVICE);
        searchItem = menu.findItem(R.id.miSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                SearchArticle(query);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                //searchView.clearFocus();

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
        return  super.onOptionsItemSelected(item);
//        switch (item.getItemId()){
//            case R.id.miSearch:
//                Search();
//                return  true;
//            case R.id.miFilter:
//                Filter();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }


    }

    private void Filter() {

    }

    private void SearchArticle(String query) {
        Log.d("Button","Search Button clicked");
        try {
            ConnectHttpClient(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSearch)
    public void onClickSearch(){
        Log.d("Button","Search Button clicked");
        String query = etSearchQuery.getText().toString();

        try {
            ConnectHttpClient(query);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //setup async http client
    private void ConnectHttpClient(String query4client) throws IOException {

        // filter url
         String queryURL = getQueryURL(query4client);
        final String[] owner = new String[1];
        // should be a singleton
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
                                                //String responseData = response.body().string();
                                                JSONObject jsonResponse = null;
                                                try {
                                                    jsonResponse = new JSONObject(response.body().string());
                                                    JSONArray resultsArray = jsonResponse.getJSONObject("response").getJSONArray("docs");
                                                    articles.addAll(Article.parseJsonArray(resultsArray.toString()));

                                                    SearchActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            articleRecyclerViewAdapter.notifyDataSetChanged();
                                                        }
                                                    });
                                                            Log.d("response","got array list of articles");
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

//    private void parseJson(String responseData) {
//        try {
//            Gson gson = new GsonBuilder().create();
//            Article[] articles = gson.fromJson(responseData, Article[].class);
//            int temp = 0;
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }

    public String getQueryURL(String query){

        //build url with params
        HttpUrl.Builder urlBuilder = HttpUrl.parse(NetworkUtils.API_URL).newBuilder();
        urlBuilder.addQueryParameter("api-key", "3ae9d158e4744dfb85debb2906d27b77");
        urlBuilder.addQueryParameter("q", query);
        String url = urlBuilder.build().toString();
        return url;
    }

}
