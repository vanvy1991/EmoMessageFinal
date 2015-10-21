package com.example.vynv.final_emo.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vynv.final_emo.HomeActivity_;
import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.adapter.RecentAdapter;
import com.example.vynv.final_emo.adapter.RecyclerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.example.vynv.final_emo.common.Util.setTabEmo;


@EFragment(R.layout.fragment_emojicon)
public class EmojiconFragment extends Fragment {
    ArrayList<String> arrData;
    ArrayList<Integer> arrDataRecent;
    MainFragment mainFragment=new MainFragment();
    @ViewById(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    private static int TEXT_TAB = 300;
    private static int GET_ICON_CODE = 222;

    @FragmentArg
    protected int numTabs;
    @FragmentArg
    protected int lenghtTab;
    private RecyclerAdapter mAdapter;
    private RecentAdapter recentAdapter;

    @AfterViews
    public void init() {
//        connectDB();
        setUpTab();
    }

    public void setUpTab() {
        if (numTabs + 1 == lenghtTab) {
            arrDataRecent=new ArrayList<>();
                    arrData = ((HomeActivity_) getActivity()).createFile(GET_ICON_CODE, 0);
            if (arrData != null) {
                for(String item:arrData){
                    if(item !=null) {
                        arrDataRecent.add(Integer.parseInt(item));
                    }

                }
                recentAdapter = new RecentAdapter(arrDataRecent, getActivity());
                mRecyclerView.setAdapter(recentAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }

        } else {
            arrData = setTabEmo(getActivity(), "icons_tab.txt", TEXT_TAB, (numTabs + 1));
            if (arrData != null) {
                mAdapter = new RecyclerAdapter(getActivity(), arrData);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }
        }
    }

}
