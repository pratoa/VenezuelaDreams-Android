package com.example.andresprato.venezueladream;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final String imageURL = getIntent().getStringExtra(BUNDLE_IMAGE_ID);
        if (imageURL == null) {
            finish();
            return;
        }

        imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(getApplicationContext()).load(imageURL).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsActivity.super.onBackPressed();
            }
        });
    }

}

