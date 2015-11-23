package com.example.vynv.final_emo.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vynv.final_emo.R;

import java.util.ArrayList;

import static com.example.vynv.final_emo.common.Util.showHideNewGrid;


/**
 * Created by vynv on 7/30/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mItems = new ArrayList<>();
    private View mViews;
    private int mIdTab;

    public RecyclerAdapter(Context context, ArrayList<String> items, int idTab) {
        if (mItems != null) {
            mItems.clear();
        } else {
            mItems = new ArrayList<>();
        }
        mItems.addAll(items);
        mContext = context;
        mIdTab = idTab;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mViews = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view, viewGroup, false);
        return new ViewHolder(mViews);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Object item = mItems.get(position);
        ArrayList<String> resultNewGrid = new ArrayList<>();
        int resID = mViews.getResources().getIdentifier("@drawable/" + item, null, mViews.getContext().getPackageName());
        viewHolder.img.setImageResource(resID);
        if(Integer.parseInt(showHideNewGrid(mItems.get(position)))==0){
            viewHolder.ivNew.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.ivNew.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        public ImageView img;
        public ImageView ivNew;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_item_recycler_view);
            ivNew = (ImageView) itemView.findViewById(R.id.img_item_new);
            itemView.setOnTouchListener(this);
        }

//        @Override
//        public boolean onLongClick(View imageView) {
//            ClipData clipData = ClipData.newPlainText("", "" + mItems.get(getPosition()));
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
//            /*start the drag - contains the data to be dragged,
//            metadata for this data and callback for drawing shadow*/
//            imageView.startDrag(clipData, shadowBuilder, imageView, 0);
//            return true;
//        }


        @Override
        public boolean onTouch(View imageView, MotionEvent event) {
            ClipData clipData = ClipData.newPlainText("", "" + mItems.get(getPosition()));
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
            /*start the drag - contains the data to be dragged,
            metadata for this data and callback for drawing shadow*/
            imageView.startDrag(clipData, shadowBuilder, imageView, 0);
            return true;
        }
    }
}
