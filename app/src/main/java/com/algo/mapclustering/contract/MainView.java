package com.algo.mapclustering.contract;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

/**
 * Created by macbookpro on 3/30/17.
 */

public interface MainView {

    void enableCompass();
    void enableMyLocation();
    void enableRotationGesture();
    void showMarkers(List<Marker> markerList);
    void setCameraViewLevel(int zlvl, GeoPoint geoPoint);
    MapView getMap();
    void showToast(String msg);
    void showRetryBtn();
    void dismissRetryBtn();

}
