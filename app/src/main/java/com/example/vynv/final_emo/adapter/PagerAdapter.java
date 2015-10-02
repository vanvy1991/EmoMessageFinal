package com.example.vynv.final_emo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vynv.final_emo.fragments.EmojiconFragment_;


/**
 * Created by nguyenvantuan on 7/30/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EmojiconFragment_.builder().build();
            default:
                break;
        }
        return EmojiconFragment_.builder().build();
    }

    @Override
    public int getCount() {
        return 0;
    }


}
