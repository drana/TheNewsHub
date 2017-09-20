package com.db.dipenrana.thenewshub.models;

/**
 * Created by dipenrana on 9/19/17.
 */

public class Article {

    //<editor-fold desc="Getters">
    public String getWebURL() {
        return webURL;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getByLine() {
        return byLine;
    }

    public String getArticleId() {
        return articleId;
    }

    //</editor-fold>

    private String webURL;
    private String imagePath;
    private String pubDate;
    private String headline;
    private String documentType;
    private String byLine;
    private String articleId;

    public Article(){

    }

}
