package com.example.andresprato.venezueladream.cards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.andresprato.venezueladream.R;
import com.example.andresprato.venezueladream.utils.DecodeBitmapTask;
import com.squareup.picasso.Picasso;

public class SliderCard extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    private DecodeBitmapTask task;

    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(Context context, String url) {
        Picasso.with(context).load(url).fit().into(imageView);
    }

    void clearContent() {
        if (task != null) {
            task.cancel(true);
        }
    }

}

