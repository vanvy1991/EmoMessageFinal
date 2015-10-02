package com.example.vynv.final_emo.model;

import java.io.Serializable;

/**
 * Created by nguyenvantuan on 7/30/15.
 */
public class Item implements Serializable {

    private int mId;
    private String mName;
    private int mResourceId;

    public Item(int id, String name, int resourceId) {
        mId = id;
        mName = name;
        mResourceId = resourceId;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getResourceId() {
        return mResourceId;
    }




}
