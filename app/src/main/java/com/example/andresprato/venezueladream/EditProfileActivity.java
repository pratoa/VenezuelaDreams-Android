package com.example.andresprato.venezueladream;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity{

    private TextView tvName;
    private TextView tvEmail;

    private TextView tvEditButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /*Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvEditButton = findViewById(R.id.editbutton);
        tvEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, EditingActivity.class);
                startActivity(intent);
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();
            tvName = findViewById(R.id.name);
            tvName.setText(name);

            String email = user.getEmail();
            tvEmail = findViewById(R.id.email);
            tvEmail.setText(email);

            String uid = user.getUid();
        }
        else {
            tvName = findViewById(R.id.name);
            tvName.setText("Name");

            tvEmail = findViewById(R.id.email);
            tvEmail.setText("Email");
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
