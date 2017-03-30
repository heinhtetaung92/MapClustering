package com.algo.mapclustering.network;

import com.algo.mapclustering.entity.GeoResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by macbookpro on 3/30/17.
 */

public interface RetrofitService {

    @GET("/heinhtetaung92/6bf811b2f375b2486c8deebf38fc28fa/raw/798229756632772283304ec2012eaea48bdcdf39/SampleGeoData")
    Observable<GeoResponse> getGeoPoints();
}
