package com.algo.mapclustering;

import android.content.Context;

import com.algo.mapclustering.entity.GeoResponse;
import com.algo.mapclustering.entity.MyGeoPoint;
import com.algo.mapclustering.network.RetrofitClient;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbookpro on 3/30/17.
 */

public class MarkerModel {

    private Context mContext;

    MarkerModel(Context context){
        mContext = context;
    }

    List<Marker> generateMarkers(MapView map){
        double lat = 22.3964d;
        List<Marker> markers = new ArrayList<>();
        for(int i=0;i<9;i++){
            Marker poiMarker = new Marker(map);

            poiMarker.setTitle("Title");
            poiMarker.setSnippet("Description");
            poiMarker.setPosition(new GeoPoint(lat,114.1095d));
            poiMarker.setIcon(mContext.getResources().getDrawable(R.drawable.marker_default));
            markers.add(poiMarker);

            lat-= 0.01;
        }

        return markers;
    }

    Observable<GeoResponse> requestMarkers(){
       return RetrofitClient.getInstance().getService().getGeoPoints();
    }

    List<Marker> convertGeoPoints(List<MyGeoPoint> pointList, MapView map){
        List<Marker> markerList = new ArrayList<>();
        for(MyGeoPoint point: pointList){
            Marker marker = new Marker(map);
            marker.setTitle(point.getmTitle());
            marker.setSnippet(point.getmDescription());
            marker.setPosition(new GeoPoint(point.getLocation().get(0), point.getLocation().get(1)));
            marker.setIcon(mContext.getResources().getDrawable(R.drawable.marker_default));
            markerList.add(marker);
        }

        return  markerList;
    }

}
