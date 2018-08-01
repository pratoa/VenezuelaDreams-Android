package com.example.andresprato.venezueladream;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditingActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;

    private FloatingActionButton fab;
    private FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        etName = findViewById(R.id.edit_name);
        etEmail = findViewById(R.id.edit_email);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.v("TAG", user.getEmail());
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

        fab = findViewById(R.id.save_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateEmail(etEmail.getText().toString());

                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
