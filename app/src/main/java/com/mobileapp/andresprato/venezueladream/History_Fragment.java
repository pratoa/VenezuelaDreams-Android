package com.mobileapp.andresprato.venezueladream;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class History_Fragment extends Fragment{



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_history_tab, container, false);


        Button setting = rootView.findViewById(R.id.settings_button);


       setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                android.support.v4.app.Fragment fragment2 = new Settings_Fragment();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });





        return rootView;
    }
}
