package com.algo.mapclustering;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.mapclustering.contract.MainView;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    MyLocationNewOverlay mLocationOverlay;
    CompassOverlay mCompassOverlay;
    RotationGestureOverlay mRotationGestureOverlay;
    MapView map;
    Context mContext;

    TextView retryBtn;

    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mPresenter = new MainPresenter(this);
        mPresenter.initMap();

    }

    void init(){
        mContext = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(mContext, PreferenceManager.getDefaultSharedPreferences(mContext));
        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        //enable multitouch
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        retryBtn = (TextView) findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mLocationOverlay.disableMyLocation();
        mLocationOverlay.disableFollowLocation();
    }

    @Override
    public void enableCompass() {
        //enable compass
        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);
    }

    @Override
    public void enableMyLocation() {
        //enable my location
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        map.getOverlays().add(this.mLocationOverlay);

        mLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                map.getController().animateTo(mLocationOverlay.getMyLocation());
            }
        });
    }

    @Override
    public void enableRotationGesture() {
        //enable rotation gesture
        mRotationGestureOverlay = new RotationGestureOverlay(this, map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(this.mRotationGestureOverlay);
    }

    @Override
    public void showMarkers(List<Marker> markerList) {
        RadiusMarkerClusterer poiMarkers = new RadiusMarkerClusterer(this);

        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();

        poiMarkers.setIcon(clusterIcon);
        map.getOverlays().add(poiMarkers);

        for(Marker marker: markerList){
            poiMarkers.add(marker);
        }

        map.getOverlays().add(poiMarkers);
    }

    @Override
    public void setCameraViewLevel(int zoomLvl, GeoPoint geoPoint) {
        //set geo point
        IMapController mapController = map.getController();
        mapController.setZoom(zoomLvl);
        mapController.setCenter(geoPoint);
    }

    @Override
    public MapView getMap() {
        return map;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRetryBtn() {
        if(retryBtn.getVisibility() != View.VISIBLE) {
            retryBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissRetryBtn() {
        if(retryBtn.getVisibility() != View.INVISIBLE) {
            retryBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.retryBtn){
            mPresenter.requestMarkers();
        }
    }
}
