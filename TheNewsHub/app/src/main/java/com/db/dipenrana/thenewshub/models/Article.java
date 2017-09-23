package com.db.dipenrana.thenewshub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dipenrana on 9/19/17.
 */

public class Article implements Parcelable{

    //region serialized fields

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("keywords")
    @Expose
    private List<Keyword> keywords = null;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private int wordCount;
    @SerializedName("score")
    @Expose
    private float score;
    @SerializedName("print_page")
    @Expose
    private String printPage;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("new_desk")
    @Expose
    private String newDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("meta")
    @Expose
    private Meta meta;
//endregion

    protected Article(Parcel in) {
        webUrl = in.readString();
        snippet = in.readString();
        documentType = in.readString();
        typeOfMaterial = in.readString();
        id = in.readString();
        wordCount = in.readInt();
        score = in.readFloat();
        printPage = in.readString();
        source = in.readString();
        pubDate = in.readString();
        newDesk = in.readString();
        sectionName = in.readString();
        uri = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    //region getters and setters
    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getNewDesk() {
        return newDesk;
    }

    public void setNewDesk(String newDesk) {
        this.newDesk = newDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(webUrl);
        parcel.writeString(snippet);
        parcel.writeString(documentType);
        parcel.writeString(typeOfMaterial);
        parcel.writeString(id);
        parcel.writeInt(wordCount);
        parcel.writeFloat(score);
        parcel.writeString(printPage);
        parcel.writeString(source);
        parcel.writeString(pubDate);
        parcel.writeString(newDesk);
        parcel.writeString(sectionName);
        parcel.writeString(uri);
    }

    // region sub class
    public class Headline {

        @SerializedName("main")
        @Expose
        private String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

    }

    public class Multimedium {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("height")
        @Expose
        private int height;
        @SerializedName("width")
        @Expose
        private int width;
        @SerializedName("rank")
        @Expose
        private int rank;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

    }

    public class Keyword {

        @SerializedName("isMajor")
        @Expose
        private String isMajor;
        @SerializedName("rank")
        @Expose
        private int rank;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        public String getIsMajor() {
            return isMajor;
        }

        public void setIsMajor(String isMajor) {
            this.isMajor = isMajor;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public class Meta {

        @SerializedName("hits")
        @Expose
        private Integer hits;
        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("time")
        @Expose
        private Integer time;

        public Integer getHits() {
            return hits;
        }

        public void setHits(Integer hits) {
            this.hits = hits;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

    }
    //endregion

    public static ArrayList<Article> parseJsonArray(String responseData) {
        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            Gson gson = new GsonBuilder().create();
            Article[] articleArray = gson.fromJson(responseData, Article[].class);
            articles = new ArrayList<Article>(Arrays.asList(articleArray));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return articles;
    }

}
