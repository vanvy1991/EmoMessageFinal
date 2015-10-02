package com.example.vynv.final_emo.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vynv.final_emo.R;
import com.example.vynv.final_emo.adapter.RecyclerAdapter;
import com.example.vynv.final_emo.sqlite.DataBaseHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.vynv.final_emo.common.Util.setTabEmo;


@EFragment(R.layout.fragment_emojicon)
public class EmojiconFragment extends Fragment {
    private static DataBaseHelper myDbHelper;
    ArrayList<String> arrData;
    @ViewById(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    private static int TEXT_TAB = 300;
    @FragmentArg
    protected int numTabs;

    private RecyclerAdapter mAdapter;

    @AfterViews
    public void init() {

//        connectDB();
        setUpTab();
    }
    public void setUpTab(){
        arrData=setTabEmo(getActivity(),"icons_tab.txt",TEXT_TAB,(numTabs + 1));
        Log.d("xxxe3",""+arrData);
        mAdapter = new RecyclerAdapter(getActivity(),arrData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }
    public void connectDB(){
        try {
            myDbHelper=new DataBaseHelper(getActivity());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //mo csdl
        try{
            myDbHelper.checkDataBase();
            myDbHelper.copyDataBase();
            myDbHelper.openDataBase();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myDbHelper.close();
    }
}
