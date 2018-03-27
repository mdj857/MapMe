package com.example.mapme_hw4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText address_edit_text;
    private Button submit_address_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address_edit_text = (EditText) findViewById(R.id.address_text);
        submit_address_btn = (Button) findViewById(R.id.submit_address_btn);

        submit_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //address_edit_text.setText("YOLO");
                String address = address_edit_text.getText().toString();
                Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                myIntent.putExtra("address", address);
                MainActivity.this.startActivity(myIntent);


            }
        });



    }
}
