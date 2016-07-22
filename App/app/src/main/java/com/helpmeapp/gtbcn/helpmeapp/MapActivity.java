package com.helpmeapp.gtbcn.helpmeapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Float lat, lng;
    String s_lat, s_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        s_lat = intent.getStringExtra("lat");
        s_lng = intent.getStringExtra("lng");
        lat = Float.parseFloat(s_lat);
        lng = Float.parseFloat(s_lng);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Barcelona and move the camera
        LatLng barcelona = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona"));
        if (s_lat.equals("41.38748400000001") && s_lng.equals("2.112728999999945")) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, 16f));
        }
    }
}
