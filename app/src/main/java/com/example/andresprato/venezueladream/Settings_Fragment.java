package com.example.andresprato.venezueladream;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings_Fragment extends Fragment {

    private View rootView;
    private View lastTouchedView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_settings_tab, container, false);



        Button history = rootView.findViewById(R.id.history_button2);



        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                android.support.v4.app.Fragment fragment2 = new History_Fragment();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });


        final TextView editProfile = rootView.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);

            }
        });

        final TextView changePassword = rootView.findViewById(R.id.change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edittext = new EditText(getActivity());
                String pass = edittext.getText().toString();
                edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edittext.setText(pass);
                edittext.setSelection(pass.length());


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.new_pass_mess)
                        .setTitle(R.string.confirm_dialog_title)
                        .setView(edittext)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user != null) {
                                    String newPassword = edittext.getText().toString();
                                    //check for pass requirements
                                    user.updatePassword(newPassword);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        final TextView paymentInformation = rootView.findViewById(R.id.payment_information);
        paymentInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentInformationActivity.class);
                startActivity(intent);
            }
        });

        final TextView notifications = rootView.findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(intent);
            }
        });

        final TextView logout = rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });


        return rootView;
    }
}
