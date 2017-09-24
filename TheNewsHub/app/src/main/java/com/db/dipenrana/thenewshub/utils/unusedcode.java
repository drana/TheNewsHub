package com.db.dipenrana.thenewshub.utils;

/**
 * Created by dipenrana on 9/24/17.
 */

public class unusedcode {
    //    //build url for search query
//    public static String getQueryURL(String query,int page,ArticleFilter newsFilter){
//
//        if(query.equals("TOP_STORY")){
//            HttpUrl.Builder urlBuilder = HttpUrl.parse(CommonUtils.API_URL).newBuilder();
//            urlBuilder.addQueryParameter("api-key",CommonUtils.NYT_API_KEY);
//            if(page >0 && page<100)
//            {
//                urlBuilder.addQueryParameter("page",Integer.toString(page));
//            }
//            return (urlBuilder.build().toString());
//        }
//        else {
//            //build url with params
//            HttpUrl.Builder urlBuilder = HttpUrl.parse(CommonUtils.API_URL).newBuilder();
//            urlBuilder.addQueryParameter("api-key",CommonUtils.NYT_API_KEY);
//            urlBuilder.addQueryParameter("q", query);
//            //check if filter applied
//            if(newsFilter != null){
//                //sort order
//                String sortOrder = newsFilter.getSortSelection().toString();
//                if(!sortOrder.isEmpty()){
//                    urlBuilder.addQueryParameter("sort",sortOrder);
//                }
//
//                //news section
//                int newsDesksSize = newsFilter.getCbNewsSection().size();
//                StringBuilder newsDeskBuilder = new StringBuilder();
//                if( newsDesksSize>0){
//                    newsDeskBuilder.append(CommonUtils.NEWSDESK);
//                    for(int i=0;i< newsDesksSize;i++){
//                        newsDeskBuilder.append(newsFilter.getCbNewsSection().get(i));
//                        newsDeskBuilder.append(CommonUtils.WHITESPACE);
//                    }
//                    newsDeskBuilder.append(")");
//                    urlBuilder.addQueryParameter("fq",newsDeskBuilder.toString());
//                }
//                //date section
//                String dateBegin = newsFilter.getSelectedDate().toString();
//                if(!dateBegin.isEmpty()){
//                    urlBuilder.addQueryParameter("begin_date",dateBegin.replaceAll("-",""));
//                }
//
//            }
//            if(page >0 && page<100)
//            {
//                urlBuilder.addQueryParameter("page",Integer.toString(page));
//            }
//            Log.d("QueryURL",urlBuilder.build().toString());
//            return (urlBuilder.build().toString());
//        }
//
//    }
}
