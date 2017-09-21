package com.db.dipenrana.thenewshub.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.dipenrana.thenewshub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dipenrana on 9/20/17.
 */

public class ArticleItemNoImage extends RecyclerView.ViewHolder{

    public TextView getTvArticleTitile() {
        return tvArticleTitile;
    }

    public TextView getTvNewsDesk() {
        return tvNewsDesk;
    }

    public TextView getTvArticleSnippet() {
        return tvArticleSnippet;
    }

    @BindView(R.id.tvArticleTitlePlain)TextView tvArticleTitile;
    @BindView(R.id.tvNewsDeskPlain) TextView tvNewsDesk;
    @BindView(R.id.tvArticleSnippetPlain) TextView tvArticleSnippet;


    public ArticleItemNoImage(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
    }
}
