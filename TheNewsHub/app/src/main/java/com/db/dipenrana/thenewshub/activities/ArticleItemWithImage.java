package com.db.dipenrana.thenewshub.activities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.dipenrana.thenewshub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dipenrana on 9/20/17.
 */

public class ArticleItemWithImage extends RecyclerView.ViewHolder {

    @BindView(R.id.tvArticleTitle) TextView tvArticleTitile;
    @BindView(R.id.tvNewsDesk) TextView tvNewsDesk;
    @BindView(R.id.tvArticleSnippet) TextView tvArticleSnippet;
    @BindView(R.id.ivArticleImage) ImageView ivArticleImage;


    public ArticleItemWithImage(View itemView) {
        super(itemView);

        ButterKnife.bind(itemView);


    }
}
