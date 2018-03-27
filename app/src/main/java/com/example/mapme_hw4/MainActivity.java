package com.example.mapme_hw4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText address;
    private Button submit_address_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address = (EditText) findViewById(R.id.address_text);
        submit_address_btn = (Button) findViewById(R.id.submit_address_btn);

        submit_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                address.setText("YOLO");
            }
        });



    }
}
