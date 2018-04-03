package com.example.mapme_hw4;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.*;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private EditText address_edit_text;
    private Button submit_address_btn;
    private TextView current_location_txtview;
    private FusedLocationProviderClient mFusedLocationClient;
    private float[] currentLatLng = {(float) 0.0, (float) 0.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address_edit_text = (EditText) findViewById(R.id.address_text);
        submit_address_btn = (Button) findViewById(R.id.submit_address_btn);
        current_location_txtview = (TextView) findViewById(R.id.cur_loc);


        submit_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //address_edit_text.setText("YOLO");
                String address = address_edit_text.getText().toString();
                Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                myIntent.putExtra("address", address);
                myIntent.putExtra("currentLatLng", currentLatLng);
                MainActivity.this.startActivity(myIntent);


            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                current_location_txtview.setText("Latitude " +location.getLatitude() + "/n" + "Longitude " + location.getLongitude());
                                currentLatLng[0] = (float) location.getLatitude();
                                currentLatLng[1] = (float) location.getLongitude();
                            } else {
                                current_location_txtview.setText("location null");
                            }
                        }
                    });

        } catch (SecurityException e){
            address_edit_text.setText("security exception!");
        }
    }
}
