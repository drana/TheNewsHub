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
 * Created by dipenrana on 9/23/17.
 */

public class TopStories implements Parcelable{

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;


    protected TopStories(Parcel in) {
        section = in.readString();
        subsection = in.readString();
        title = in.readString();
        _abstract = in.readString();
        url = in.readString();
    }

    public static final Creator<TopStories> CREATOR = new Creator<TopStories>() {
        @Override
        public TopStories createFromParcel(Parcel in) {
            return new TopStories(in);
        }

        @Override
        public TopStories[] newArray(int size) {
            return new TopStories[size];
        }
    };

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(section);
        parcel.writeString(subsection);
        parcel.writeString(title);
        parcel.writeString(_abstract);
        parcel.writeString(url);
    }

    public class Multimedium {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("format")
        @Expose
        private String format;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("copyright")
        @Expose
        private String copyright;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

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

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }
    }
    public static ArrayList<TopStories> parseJsonTopStories(String responseData) {
        ArrayList<TopStories> articles = new ArrayList<TopStories>();

        try {
            Gson gson = new GsonBuilder().create();
            TopStories[] articleArray = gson.fromJson(responseData, TopStories[].class);
            articles = new ArrayList<TopStories>(Arrays.asList(articleArray));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return articles;
    }

    }
