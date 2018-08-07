package com.mobileapp.andresprato.venezueladream.utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class DownloadImageTask extends AsyncTask<String, Void, Integer> {

    private ProgressBar progressBar;

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected Integer doInBackground(String... strings) {
        Integer result = 0;

        return null;
    }
}
