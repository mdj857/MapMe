package com.example.mapme_hw4;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    private String address;
    private float[] currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get address from main activity

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        currentLatLng = intent.getFloatArrayExtra("currentLatLng");

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

        // get lat and long from google API
        String reqURL = "http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false";
        Double[] result = null;
        MyAsyncTask task = new MyAsyncTask();
        task.execute(reqURL);
        try {
            result = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // display current address

        LatLng currLocation = new LatLng(currentLatLng[0], currentLatLng[1]);
        MarkerOptions currMarker = new MarkerOptions().position(currLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        mMap.addMarker(currMarker);

        // display marker for address
        LatLng latLng = new LatLng(result[0], result[1]);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.addMarker(marker);
        mMap.animateCamera(camera);


    }
}
