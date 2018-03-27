package com.example.mapme_hw4;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                // actual--see https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
                Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+address));
                startActivity(searchAddress);
            }
        });



    }
}
