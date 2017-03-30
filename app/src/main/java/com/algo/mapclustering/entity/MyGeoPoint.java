package com.algo.mapclustering.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by macbookpro on 3/30/17.
 */

public class MyGeoPoint {
    @SerializedName("title")
    String mTitle;

    @SerializedName("description")
    String mDescription;

    @SerializedName("location")
    List<Double> location;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }
}
