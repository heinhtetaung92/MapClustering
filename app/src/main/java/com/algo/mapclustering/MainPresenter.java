package com.algo.mapclustering;

import android.content.Context;

import com.algo.mapclustering.contract.MainPresenterContract;
import com.algo.mapclustering.contract.MainView;
import com.algo.mapclustering.entity.GeoResponse;

import org.osmdroid.util.GeoPoint;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by macbookpro on 3/30/17.
 */

public class MainPresenter implements MainPresenterContract{

    final private GeoPoint hongkongGeoPoint = new GeoPoint(22.3964, 114.1095);
    final private int zoomLevel = 11;

    MainView mMainView;
    MarkerModel model;

    Context mContext;

    MainPresenter(Context context){
        this.mMainView = (MainView) context;
        model = new MarkerModel(context);
        this.mContext = context;
    }

    @Override
    public void initMap() {
        mMainView.setCameraViewLevel(zoomLevel, hongkongGeoPoint);
        mMainView.enableMyLocation();
        mMainView.enableCompass();
        mMainView.enableRotationGesture();
        requestMarkers();

    }

    @Override
    public void getMarkers() {
        model.requestMarkers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeoResponse>(){


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeoResponse geoResponse) {
                        mMainView.showMarkers(model.convertGeoPoints(geoResponse.getData(), mMainView.getMap()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.showToast("Sorry, Something went wrong!");
                        mMainView.showRetryBtn();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void requestMarkers() {
        mMainView.dismissRetryBtn();
        if(ConnectionStatus.isConnected(mContext)) {
            getMarkers();
        }else{
            mMainView.showToast("You need connection to view GeoPoints");
            mMainView.showRetryBtn();
        }
    }


}
