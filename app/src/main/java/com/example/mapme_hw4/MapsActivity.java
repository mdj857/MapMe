package com.example.mapme_hw4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;

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
    private static float[] currentLatLng;
    private static Double[] addressLatitudeLongitude = {(Double) 1.0, (Double) 2.0};

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
        Object[] result = null;
        MyAsyncTask task = new MyAsyncTask();
        task.execute(reqURL);
        try {
            result = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // display current address

        LatLng currLocation = new LatLng(currentLatLng[0], currentLatLng[1]);
        MarkerOptions currMarker = new MarkerOptions().position(currLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Your location");

        mMap.addMarker(currMarker);

        // display marker for address
        LatLng latLng = new LatLng((Double) result[0], (Double) result[1]);
        MarkerOptions marker = new MarkerOptions().position(latLng).title((String) result[2]);
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.addMarker(marker);
        mMap.animateCamera(camera);

        addressLatitudeLongitude[0] = (Double) result[0];
        addressLatitudeLongitude[1] = (Double) result[1];

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setText("You are " + Math.round(getDistanceFromCurrent()) + "miles from that address!");

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(et);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }


    /* Gets distance from current location to address*/
    private static double getDistanceFromCurrent() {
        double lat1 = (double) currentLatLng[0];
        double lon1 = (double) currentLatLng[1];
        double lat2 = addressLatitudeLongitude[0];
        double lon2 = addressLatitudeLongitude[1];

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return dist;
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
