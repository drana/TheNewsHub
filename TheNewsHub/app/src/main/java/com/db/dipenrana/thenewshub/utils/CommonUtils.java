package com.db.dipenrana.thenewshub.utils;

import android.util.Log;

import com.db.dipenrana.thenewshub.models.ArticleFilter;

import okhttp3.HttpUrl;

/**
 * Created by dipenrana on 9/18/17.
 */

public class CommonUtils {

    public static final String NYT_API_KEY = "3ae9d158e4744dfb85debb2906d27b77";
    //public static final String API_URL = "api.nytimes.com/svc/search/v2/articlesearch.json?api-key=3ae9d158e4744dfb85debb2906d27b77";

    public static final String API_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    public static final String IMAGE_URL_PREFIX = "http://www.nytimes.com/";

    public static final String WHITESPACE = " ";

    public static final String NEWSDESK = "news_desk:(";

    //build url for search query
    public static String getQueryURL(String query,int page,ArticleFilter newsFilter){

        //build url with params
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CommonUtils.API_URL).newBuilder();
        urlBuilder.addQueryParameter("api-key",CommonUtils.NYT_API_KEY);
        urlBuilder.addQueryParameter("q", query);
        //check if filter applied
        if(newsFilter != null){
            //sort order
            urlBuilder.addQueryParameter("sort",newsFilter.getSortSelection().toString());
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
            String dateBegin = newsFilter.getSelectedDate().toString().replaceAll("-","");
            urlBuilder.addQueryParameter("begin_date",dateBegin);

        }
        if(page >0 && page<100)
        {
            urlBuilder.addQueryParameter("page",Integer.toString(page));
        }
        Log.d("QueryURL",urlBuilder.build().toString());
        return (urlBuilder.build().toString());
    }
}
