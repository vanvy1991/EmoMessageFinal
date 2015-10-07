package com.example.vynv.final_emo.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vynv.final_emo.R;

import java.util.ArrayList;


/**
 * Created by nguyenvantuan on 7/30/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mItems = new ArrayList<>();
    private View mViews;

    public RecyclerAdapter(Context context, ArrayList<String> items) {
        if (mItems != null) {
            mItems.clear();
        } else {
            mItems = new ArrayList<>();
        }
        mItems.addAll(items);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mViews = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view, viewGroup, false);
        return new ViewHolder(mViews);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Object item = mItems.get(position);
        int resID = mViews.getResources().getIdentifier("@drawable/" + mItems.get(position), null, mViews.getContext().getPackageName());
        viewHolder.img.setImageResource(resID);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_item_recycler_view);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View imageView) {
            ClipData clipData = ClipData.newPlainText("", "" + mItems.get(getPosition()));
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
            /*start the drag - contains the data to be dragged,
            metadata for this data and callback for drawing shadow*/
            imageView.startDrag(clipData, shadowBuilder, imageView, 0);
            return true;
        }
    }
}
