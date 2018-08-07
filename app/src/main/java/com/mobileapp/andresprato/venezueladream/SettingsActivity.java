package com.mobileapp.andresprato.venezueladream;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment1 = new Settings_Fragment();
            transaction.replace(R.id.container, fragment1);
            transaction.commit();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
            Log.v("TAG", "tacooooooooooooooo");
             //onBackPressed();
        finish();
             return true;
    }

}
