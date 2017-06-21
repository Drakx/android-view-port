/*
 * Kai Windle Copyright (c) 15/06/17 09:27
 * MainActivity.java
 * Boxoverlay
 */

package com.kaiwindle.boxoverlay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btnCode = (Button) findViewById(R.id.btn_code);
        btnCode.setOnClickListener(this);

        final Button btnXML = (Button) findViewById(R.id.btn_xml);
        btnXML.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String key = "";
        switch (v.getId()) {
            case R.id.btn_code:
                key = getString(R.string.bundle_key_code);
                break;

            case R.id.btn_xml:
                key = getString(R.string.bundle_key_xml);
                break;

            default:
                break;
        }

        Intent intent = new Intent(this, OverlayView.class);
        intent.putExtra(getString(R.string.bundle_key), key);
        startActivity(intent);


    }
}



