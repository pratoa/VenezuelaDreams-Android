package com.mobileapp.andresprato.venezueladream;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class NotificationsActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
