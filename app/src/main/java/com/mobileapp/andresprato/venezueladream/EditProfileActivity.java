package com.mobileapp.andresprato.venezueladream;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity{

    private TextView tvName;
    private TextView tvEmail;

    private FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, EditingActivity.class);
                startActivity(intent);
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvName = findViewById(R.id.name);
        tvEmail = findViewById(R.id.email);
        if (user != null) {

            String name = user.getDisplayName();

            tvName.setText(name);

            String email = user.getEmail();

            tvEmail.setText(email);

            String uid = user.getUid();
        }
        else {

            tvName.setText("Name");


            tvEmail.setText("Email");
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
