package com.example.vynv.final_emo.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;

import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.adapter.PagerFragmentAdapter;
import com.example.vynv.final_emo.common.HorizontalListView;
import com.example.vynv.final_emo.model.itemFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
    PagerFragmentAdapter mPagerFragmentApdater;
    private Context mContext;
    @ViewById(R.id.horizontalListviewIconTab)
    protected HorizontalListView horizontalListView;

    @AfterViews
    public void initView() {
        mContext = getActivity();
        initPagerFragment();

        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClick(position);
            }
        });
    }

    public void initPagerFragment() {

        ArrayList<itemFragment> itemFragment = new ArrayList<>();
        itemFragment.add(new itemFragment("ICONS"));
        itemFragment.add(new itemFragment("CAMERA"));
        itemFragment.add(new itemFragment("SETTING"));
        mPagerFragmentApdater = new PagerFragmentAdapter(mContext, itemFragment);
        horizontalListView.setAdapter(mPagerFragmentApdater);

    }

    public void itemClick(int pos) {
        if (pos == 0) {
            MainFragment f = MainFragment_.builder().build();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frMain, f);
            fragmentTransaction.commit();
        }
//        else if(pos==1){
//            CameraFragment f=CameraFragment_.builder().build();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frHome, f);
//            fragmentTransaction.commit();
//        }

    }
}
