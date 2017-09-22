package com.db.dipenrana.thenewshub.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.dipenrana.thenewshub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dipenrana on 9/21/17.
 */

public class ArticleItemCardView extends RecyclerView.ViewHolder {

    public TextView getTvArticleTitile() {
        return tvArticleTitile;
    }

    public TextView getTvNewsDesk() {
        return tvNewsDesk;
    }

    public TextView getTvArticleSnippet() {
        return tvArticleSnippet;
    }

    public ImageView getIvArticleImage() {
        return ivArticleImage;
    }


    @BindView(R.id.tvArticleTitle) TextView tvArticleTitile;
    @BindView(R.id.tvNewsDesk) TextView tvNewsDesk;
    @BindView(R.id.tvArticleSnippet) TextView tvArticleSnippet;
    @BindView(R.id.ivArticleImage) ImageView ivArticleImage;


    public ArticleItemCardView(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);


    }
}
