package com.example.vynv.final_emo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.model.itemFragment;

import java.util.ArrayList;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by vynv on 9/23/15.
 */
public class PagerFragmentAdapter extends ArrayAdapter<itemFragment> {
    LayoutInflater layoutInflater;
    public PagerFragmentAdapter(Context context,ArrayList<itemFragment> itemFragment){
        super(context,0,itemFragment);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemFragment items=getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.item_tab_fragment,null);
            viewHolder=new ViewHolder();
            viewHolder.tvItemFragment=(TextView)convertView.findViewById(R.id.tvItemFragment);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvItemFragment.setText(items.getItemFragemnts());
        return convertView;
    }

    private class ViewHolder{
        TextView tvItemFragment;
    }
}
