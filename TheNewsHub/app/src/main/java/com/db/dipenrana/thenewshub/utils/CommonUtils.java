package com.db.dipenrana.thenewshub.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.activities.SearchActivity;
import com.db.dipenrana.thenewshub.models.ArticleFilter;

import java.util.Random;

import okhttp3.HttpUrl;

/**
 * Created by dipenrana on 9/18/17.
 */

public class CommonUtils {

    public static final String NYT_API_KEY = "3ae9d158e4744dfb85debb2906d27b77";


    public static final String API_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    public static final String HOME_API_URL = "https://api.nytimes.com/svc/topstories/v2/home.json";

    public static final String IMAGE_URL_PREFIX = "http://www.nytimes.com/";

    public static final String WHITESPACE = " ";

    public static final String NEWSDESK = "news_desk:(";

    public static String[] SORT_ORDER = new String[]{"Relevance","newest","oldest"};



    //build url for search query
    public static String getQueryURL(String query,int page,ArticleFilter newsFilter){

            //build url with params
            HttpUrl.Builder urlBuilder = HttpUrl.parse(CommonUtils.API_URL).newBuilder();
            urlBuilder.addQueryParameter("api-key",CommonUtils.NYT_API_KEY);
            if(!query.equals("TOP_STORY")) {
                urlBuilder.addQueryParameter("q", query);
            }
            //check if filter applied
            if(newsFilter != null){
                //sort order
                String sortOrder = newsFilter.getSortSelection().toString();
                if(!sortOrder.isEmpty() && !sortOrder.equals("Relevance")){
                    urlBuilder.addQueryParameter("sort",sortOrder);
                }

                //news section
                int newsDesksSize = newsFilter.getCbNewsSection().size();
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
                //date section
                String dateBegin = newsFilter.getSelectedDate().toString();
                if(!dateBegin.isEmpty()){
                    urlBuilder.addQueryParameter("begin_date",dateBegin.replaceAll("-",""));
                }

            }
            if(page >0 && page<100)
            {
                urlBuilder.addQueryParameter("page",Integer.toString(page));
            }
            Log.d("QueryURL",urlBuilder.build().toString());
            return (urlBuilder.build().toString());


    }

    public static int getNewsDeskColor(Context mContext,String newDesk) {
        int bkColor = getRandomColor();
        switch (newDesk){
            case "Sports":
                bkColor = ContextCompat.getColor(mContext, R.color.color_sports);
                break;
            case "Arts":
                bkColor = ContextCompat.getColor(mContext, R.color.color_arts);
                break;
            case "Automobiles":
                bkColor = ContextCompat.getColor(mContext, R.color.color_auto);
                break;
            case "Business":
                bkColor = ContextCompat.getColor(mContext, R.color.color_business);
                break;
            case "Classifieds":
                bkColor = ContextCompat.getColor(mContext, R.color.color_fields);
                break;
            case "Fashion":
                bkColor = ContextCompat.getColor(mContext, R.color.color_fashion);
                break;
            case "Food":
                bkColor = ContextCompat.getColor(mContext, R.color.color_food);
                break;
            case "Foreign":
                bkColor = ContextCompat.getColor(mContext, R.color.color_foreign);
                break;
            case "Generations":
                bkColor = ContextCompat.getColor(mContext, R.color.primary_dark);
                break;
            case "Health":
                bkColor = ContextCompat.getColor(mContext, R.color.color_health);
                break;
            case "Media":
                bkColor = ContextCompat.getColor(mContext, R.color.color_media);
                break;
            case "Movies":
                bkColor = ContextCompat.getColor(mContext, R.color.color_movies);
                break;
            case "Science":
                bkColor = ContextCompat.getColor(mContext, R.color.color_science);
                break;
            case "Technology":
                bkColor = ContextCompat.getColor(mContext, R.color.color_tech);
                break;
            case "U.S.":
                bkColor = ContextCompat.getColor(mContext, R.color.accent);
                break;
            case "None":
                bkColor = ContextCompat.getColor(mContext, R.color.primary_dark);
                break;



        }
        return bkColor;
    }

    public static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static Boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
