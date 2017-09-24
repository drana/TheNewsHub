package com.db.dipenrana.thenewshub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.activities.ArticleItemCardView;
import com.db.dipenrana.thenewshub.activities.ArticleItemNoImageCardView;
import com.db.dipenrana.thenewshub.models.Article;
import com.db.dipenrana.thenewshub.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
                //View vPoster = inflater.inflate(R.layout.article_item_with_image, parent, false);
                View vPoster = inflater.inflate(R.layout.article_item_cardview, parent, false);
                viewHolder = new ArticleItemCardView(vPoster);
                break;
            case NoImage:
                View vLandscape = inflater.inflate(R.layout.article_item_no_image_cardview, parent, false);
                viewHolder = new ArticleItemNoImageCardView(vLandscape);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_activated_1, parent, false);
                viewHolder = new ArticleItemCardView(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case Image:
                ArticleItemCardView vh1 = (ArticleItemCardView) holder;
                BindItemWithImage(vh1,position);
                break;
            case NoImage:
                ArticleItemNoImageCardView vh2 = (ArticleItemNoImageCardView) holder;
                BindItemWithNoImage(vh2,position);
                //configureLandscapeActivity(vh2, position);
                break;
            default:
                ArticleItemCardView vh = (ArticleItemCardView) holder;
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


    private void BindItemWithImage(ArticleItemCardView holder, int position)  {
        // Get the data model based on position
        Article article = mArticles.get(position);
        TextView tvTitle = holder.getTvArticleTitile();
        TextView tvNewsDesk = holder.getTvNewsDesk();
        TextView tvSnippet = holder.getTvArticleSnippet();
        ImageView ivArticleImage = holder.getIvArticleImage();
        ivArticleImage.setImageResource(0);

        String imgURL = CommonUtils.IMAGE_URL_PREFIX + article.getMultimedia().get(0).getUrl();

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
//        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/Raleway-Regular.ttf");
//        tvTitle.setTypeface(font);

        tvNewsDesk.setText(article.getNewDesk());
        tvSnippet.setText(article.getSnippet());
//        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/Raleway-Regular.ttf");
//        tvSnippet.setTypeface(font);
//        tvSnippet.setTypeface(font);
        int backgroundColor = CommonUtils.getNewsDeskColor(mContext, article.getNewDesk());
        tvNewsDesk.setBackgroundColor(backgroundColor);

    }


    private void BindItemWithNoImage(ArticleItemNoImageCardView holder, int position) {

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
