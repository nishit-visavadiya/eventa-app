package com.parthpaija.eventaapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Time_pass extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pass);

        imageView=(ImageView) findViewById(R.id.image);

        String url="https://firebasestorage.googleapis.com/v0/b/eventaapp-ae56c.appspot.com/o/uploads%2F1586159930373.jpg?alt=media&token=23558d3f-fc9d-45c1-9727-7cc2402df78a";//Retrieved url as mentioned above

        Glide.with(getApplicationContext()).load(url).into(imageView);
    }
}
