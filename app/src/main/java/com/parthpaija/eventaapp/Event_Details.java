package com.parthpaija.eventaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Event_Details extends AppCompatActivity {

    TextView txt_event;
    ImageView img_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__details);

        txt_event = (TextView) findViewById(R.id.Details_txt_name);
        img_event = (ImageView) findViewById(R.id.Details_img_upload);

        String s = getIntent().getStringExtra("event_name");
        String s2 = getIntent().getStringExtra("imag_url");

        txt_event.setText(s);
        Glide.with(getApplicationContext()).load(s2).fitCenter().centerCrop().into(img_event);
    }
}
