package com.db.dipenrana.thenewshub.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.activities.ArticleItemNoImage;
import com.db.dipenrana.thenewshub.activities.ArticleItemWithImage;
import com.db.dipenrana.thenewshub.models.Article;
import com.db.dipenrana.thenewshub.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by dipenrana on 9/20/17.
 */

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //store context and articles.
    ArrayList<Article> mArticles = new ArrayList<Article>();
    Context mContext;

    private final int Image = 0, NoImage = 1;


    //constructor
    public ArticleRecyclerViewAdapter(Context context, ArrayList<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Image:
                View vPoster = inflater.inflate(R.layout.article_item_with_image, parent, false);
                viewHolder = new ArticleItemWithImage(vPoster);
                break;
            case NoImage:
                View vLandscape = inflater.inflate(R.layout.article_item_no_image, parent, false);
                viewHolder = new ArticleItemNoImage(vLandscape);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_activated_1, parent, false);
                viewHolder = new ArticleItemWithImage(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case Image:
                ArticleItemWithImage vh1 = (ArticleItemWithImage) holder;
                BindItemWithImage(vh1,position);
                break;
            case NoImage:
                ArticleItemNoImage vh2 = (ArticleItemNoImage) holder;
                BindItemWithNoImage(vh2,position);
                //configureLandscapeActivity(vh2, position);
                break;
            default:
                ArticleItemWithImage vh = (ArticleItemWithImage) holder;
                //configureDefaultViewHolder(vh, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        // decide which which view to use
        Article item = mArticles.get(position);
        ArrayList<Article.Multimedium> itemImageURLs = new ArrayList<Article.Multimedium>();
        if(item.getMultimedia().size() > 0)
        {
            Log.d("getItemViewType","returned Image");
            return Image;
        }
        else {
            Log.d("getItemViewType","returned NoImage");
            return NoImage;
        }

        //itemImageURLs.addAll(item.getMultimedia());

//        if(itemImageURLs.size()>0)
//        {
//            //Article.Multimedium itemURL = itemImageURLs.get(0);
//            Log.d("getItemViewType","returned Image");
//            return Image;
//        }
//        else if(itemImageURLs.isEmpty()){
//            Log.d("getItemViewType","returned NoImage");
//            return NoImage;
//
//        }
 //       return -1;

    }


    private void BindItemWithImage(ArticleItemWithImage holder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);
        TextView tvTitle = holder.getTvArticleTitile();
        TextView tvNewsDesk = holder.getTvNewsDesk();
        TextView tvSnippet = holder.getTvArticleSnippet();
        ImageView ivArticleImage = holder.getIvArticleImage();
        ivArticleImage.setImageResource(0);

        String imgURL = NetworkUtils.IMAGE_URL_PREFIX + article.getMultimedia().get(0).getUrl();

        Picasso.with(mContext)
                .load(imgURL)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .noFade()
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivArticleImage);

        tvTitle.setText(article.getHeadline().getMain());
        tvNewsDesk.setText(article.getNewDesk());
        tvSnippet.setText(article.getSnippet());
    }


    private void BindItemWithNoImage(ArticleItemNoImage holder, int position) {

        // Get the data model based on position
        Article article = mArticles.get(position);
        TextView tvTitle = holder.getTvArticleTitile();
        TextView tvNewsDesk = holder.getTvNewsDesk();
        TextView tvSnippet = holder.getTvArticleSnippet();

        tvTitle.setText(article.getHeadline().getMain());
        tvNewsDesk.setText(article.getNewDesk());
        tvSnippet.setText(article.getSnippet());

    }

}
