package com.db.dipenrana.thenewshub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipenrana on 9/23/17.
 */

public class ArticleFilter implements Parcelable{
    protected ArticleFilter(Parcel in) {
        selectedDate = in.readString();
        cbNewsSection = in.createStringArrayList();
        sortSelection = in.readString();
    }

    //required empty contructor;
    public ArticleFilter(String date,String sortSelection, List<String> newsSection)
    {
        this.selectedDate = date;
        this.sortSelection = sortSelection;
        this.cbNewsSection = newsSection;
    }
    public static final Creator<ArticleFilter> CREATOR = new Creator<ArticleFilter>() {
        @Override
        public ArticleFilter createFromParcel(Parcel in) {
            return new ArticleFilter(in);
        }

        @Override
        public ArticleFilter[] newArray(int size) {
            return new ArticleFilter[size];
        }
    };

    public String getSelectedDate() {
        return selectedDate;
    }

    public List<String> getCbNewsSection() {
        return cbNewsSection;
    }

    public String getSortSelection() {
        return sortSelection;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setCbNewsSection(List<String> cbNewsSection) {
        this.cbNewsSection = cbNewsSection;
    }

    public void setSortSelection(String sortSelection) {
        this.sortSelection = sortSelection;
    }

    String selectedDate;
    List<String> cbNewsSection = new ArrayList<String>();
    String sortSelection;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(selectedDate);
        parcel.writeStringList(cbNewsSection);
        parcel.writeString(sortSelection);
    }
}
