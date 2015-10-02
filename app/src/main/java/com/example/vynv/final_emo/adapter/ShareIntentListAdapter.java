package com.example.vynv.final_emo.adapter;

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vynv.final_emo.R;


/**
 * Copyright © 2015 AsianTech inc.
 * Created by vynv on 8/21/15.
 */
public class ShareIntentListAdapter extends ArrayAdapter {
    private final Activity context;
    Object[] items;


    public ShareIntentListAdapter(Activity context,Object[] items) {

        super(context, R.layout.social_share, items);
        this.context = context;
        this.items = items;

    }// end HomeListViewPrototype

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.social_share, null, true);

        // set share name
        TextView shareName = (TextView) rowView.findViewById(R.id.shareName);

        // Set share image
        ImageView imageShare = (ImageView) rowView.findViewById(R.id.shareImage);

        // set native name of App to share
        shareName.setText(((ResolveInfo)items[position]).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
        // share native image of the App to share
        imageShare.setImageDrawable(((ResolveInfo)items[position]).activityInfo.applicationInfo.loadIcon(context.getPackageManager()));

        return rowView;
    }// end getView

}// end main onCreate
