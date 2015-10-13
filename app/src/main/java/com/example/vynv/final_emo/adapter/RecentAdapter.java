package com.example.vynv.final_emo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vynv.final_emo.R;

import java.util.ArrayList;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by vynv on 10/9/15.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private ArrayList<Integer> mIcons;
    private Context mContext;
    private View mViews;

    public RecentAdapter( ArrayList<Integer> icons, Context context){
        Log.d("xxx11","Da vao:"+icons);
        if (mIcons != null) {
            mIcons.clear();
        } else {
            Log.d("xxx12","Da vao:"+icons);
            mIcons = new ArrayList<>();
        }
        mIcons.addAll(icons);
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mViews = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(mViews);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Object item = mIcons.get(position);
//        int resID = mViews.getResources().getIdentifier("@drawable/" + item, null, mViews.getContext().getPackageName());
        viewHolder.img.setImageResource((Integer) item);
    }

    @Override
    public int getItemCount() {
        return mIcons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_item_recycler_view);
        }

    }
}
