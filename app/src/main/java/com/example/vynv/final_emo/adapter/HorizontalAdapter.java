package com.example.vynv.final_emo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vynv.final_emo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;


/**
 * Copyright © 2015 AsianTech inc.
 * Created by vynv on 9/16/15.
 */
public class HorizontalAdapter extends BaseAdapter {

    private ArrayList<String> mTabName;
    private ArrayList<String> mIcons;
    private Context mContext;
    private LayoutInflater mInflater;
    private DisplayImageOptions options;

    public HorizontalAdapter(Context mContext, ArrayList<String> tabName, ArrayList<String> icons) {
        Log.d("xxx",icons+"---"+tabName);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        mTabName = new ArrayList<>();
        mTabName.addAll(tabName);
        mIcons = new ArrayList<>();
        mIcons.addAll(icons);

    }

    @Override
    public int getCount() {
        return mTabName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tab, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tabName = (TextView) convertView.findViewById(R.id.txtTab);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.img_item_tab);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mTabName.get(position) == null) {
            viewHolder.tabName.setText("");
        } else {
            viewHolder.tabName.setText(mTabName.get(position));
        }
        int resID = convertView.getResources().getIdentifier("@drawable/" + mIcons.get(position), null, mContext.getPackageName());
//        Uri uri = Uri.parse("android.resource://com.example.vynv.final_emo/" + resID);
        Bitmap b = BitmapFactory.decodeResource(convertView.getResources(), resID);
        Log.d("xxxresID", "" + b.getWidth());
        viewHolder.icon.setImageBitmap(b);
//        ImageLoader.getInstance().displayImage("drawable://" + uri, viewHolder.icon, options);
//        viewHolder.icon.setImageResource(resID);
        return convertView;
    }

    private class ViewHolder {
        TextView tabName;
        ImageView icon;

    }
}
