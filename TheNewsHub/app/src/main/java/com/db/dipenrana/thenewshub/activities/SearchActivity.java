package com.db.dipenrana.thenewshub.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);



    }

    @OnClick(R.id.btnSearch)
    public void onClickSearch(){
        Log.d("Button","Search Button clicked");
        String query = etSearchQuery.getText().toString();

        //Toast.makeText(this, query,Toast.LENGTH_LONG).show();
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
                                                String responseData = response.body().string();
                                                JSONObject json = null;
                                                try {
                                                    json = new JSONObject(responseData);
                                                     //owner[0] = json.getString("titile");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                if (!response.isSuccessful()) {
                                                    throw new IOException("Unexpected code " + response);
                                                }
                                            }
                                        });

        Log.d("Connect", "Got response from NYT");

    }

     public String getQueryURL(String query){

        //build url with params
        HttpUrl.Builder urlBuilder = HttpUrl.parse(NetworkUtils.API_URL).newBuilder();
        urlBuilder.addQueryParameter("api-key", "3ae9d158e4744dfb85debb2906d27b77");
        urlBuilder.addQueryParameter("q", query);
        String url = urlBuilder.build().toString();
        return url;
    }

}
