package com.mobileapp.andresprato.venezueladream;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DonationActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextView name_text;
    ImageView child_image;

    EditText credit_card;
    EditText exp_date;
    EditText cvc;
    EditText zip_code;
    RadioGroup radioGroup;
    Button donate_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = this.getIntent().getExtras();
        final String name = bundle.getString("name");
        int age = bundle.getInt("age");
        final String child_id = bundle.getString("child_id");
        String url = bundle.getString("image_url");

        Log.v("AQUI", url);

        name_text = findViewById(R.id.name_and_age);
        child_image = findViewById(R.id.donation_image);

        name_text.setText(name + ", " + age);
        Picasso.with(this).load(url).resize(650, 650).into(child_image);

        donate_button = findViewById(R.id.donation_button);
        credit_card = findViewById(R.id.credit_card_number);
        exp_date = findViewById(R.id.exp_date);
        cvc = findViewById(R.id.cvc);
        zip_code = findViewById(R.id.zip_code);
        radioGroup = findViewById(R.id.radioGroup);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        exp_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && (s.length() % 3) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if ('/' == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 3) == 0) {
                    char c = s.charAt(s.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf("/")).length <= 2) {
                        s.insert(s.length() - 1, String.valueOf("/"));
                    }
                }
            }
        });

        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("AQUI", "Button clicked");

                String creditCard = credit_card.getText().toString();
                String zipCode = zip_code.getText().toString();
                String c = cvc.getText().toString();
                String exp = exp_date.getText().toString();

                if (creditCard.isEmpty()) {
                    builder.setCancelable(true);
                    builder.setTitle("Error!");
                    builder.setMessage("Credit Card text field is empty!");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (zipCode.isEmpty()) {
                    builder.setCancelable(true);
                    builder.setTitle("Error!");
                    builder.setMessage("Zip Code text field is empty!");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (c.isEmpty()) {
                    builder.setCancelable(true);
                    builder.setTitle("Error!");
                    builder.setMessage("CVC text field is empty!");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (exp.isEmpty()) {
                    builder.setCancelable(true);
                    builder.setTitle("Error!");
                    builder.setMessage("Expiration Date text field is empty!");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Log.v("AQUI", "All good!");
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    String transactio_id = mDatabase.push().getKey();

                    RadioButton rd = findViewById(radioGroup.getCheckedRadioButtonId());
                    String amount = rd.getText().toString();

                    WriteFields transaction = new WriteFields(child_id, creditCard, exp, zipCode, c, amount);

                    Log.v("AQUI", currentFirebaseUser.getUid());
                    mDatabase.child("transactions").child("userId").child(currentFirebaseUser.getUid())
                            .child("transactionId").child(transactio_id).setValue(transaction)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            // ...
                            Log.v("AQUI", "Transaction Successfull! " + child_id);
                            builder.setCancelable(true);
                            builder.setTitle("Thank you for donating!!");
                            builder.setMessage("Your donation will help " + name + " have food!");
                            builder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setClass(DonationActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    // ...
                                    Log.v("AQUI", "Transaction Failed!");
                                    builder.setCancelable(true);
                                    builder.setTitle("Error processing transaction");
                                    builder.setMessage("There was a network issue with the transaction");
                                    builder.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });
                }
            }

        });
    }
}

class WriteFields {

    String credit_card;
    String exp_date;
    String cvc;
    String child_id;
    String zip_code;
    String amount;

    public WriteFields(String childId, String credit, String exp, String zip, String cv, String am) {

        credit_card = credit;
        exp_date = exp;
        cvc = cv;
        child_id = childId;
        zip_code = zip;
        amount = am;
    }

}