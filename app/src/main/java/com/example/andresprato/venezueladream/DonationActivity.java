package com.example.andresprato.venezueladream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class DonationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        int age = bundle.getInt("age");
        String child_id = bundle.getString("child_id");
        String url = bundle.getString("image_url");

    }
}