package com.algo.mapclustering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 3/30/17.
 */

public class GeoResponse {
    @SerializedName("data") @Expose
    private List<MyGeoPoint> data = new ArrayList<>();

    public List<MyGeoPoint> getData() {
        return data;
    }

    public void setData(List<MyGeoPoint> data) {
        this.data = data;
    }
}
