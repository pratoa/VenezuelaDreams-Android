package com.mobileapp.andresprato.venezueladream.cards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileapp.andresprato.venezueladream.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderCard> {

    private final int count;
    private final Context mContext;
    private final List<String> URLs;
    private final View.OnClickListener listener;

    public SliderAdapter(Context context, List<String> imgURLs, int count, View.OnClickListener listener) {
        this.mContext = context;
        this.URLs = imgURLs;
        this.count = count;
        this.listener = listener;
    }

    @Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }

        return new SliderCard(view);
    }

    @Override
    public void onBindViewHolder(SliderCard holder, int position) {
        //holder.setContent(content[position % content.length]);
        holder.setContent(mContext, URLs.get(position));
    }

    @Override
    public void onViewRecycled(SliderCard holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return count;
    }

}
