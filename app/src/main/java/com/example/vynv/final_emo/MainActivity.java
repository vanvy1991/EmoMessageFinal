package com.example.vynv.final_emo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.vynv.final_emo.fragments.MainFragment;
import com.example.vynv.final_emo.fragments.MainFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    

    @AfterViews
    public void initViews(){
        initMainFragment();
    }
    public void initMainFragment(){
        MainFragment mainFragment= MainFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameMain, mainFragment);
        fragmentTransaction.commit();

    }
}
