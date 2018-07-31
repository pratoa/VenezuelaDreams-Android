package com.example.andresprato.venezueladream;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditingActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        etName = findViewById(R.id.edit_email);
        etEmail = findViewById(R.id.edit_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();
            etName.setText(name);

            String email = user.getEmail();
            etEmail.setText(email);

            String uid = user.getUid();
        }
        else {

            etName.setText("Name");
            etEmail.setText("Email");
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
