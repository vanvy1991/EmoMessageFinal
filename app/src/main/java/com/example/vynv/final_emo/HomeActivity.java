package com.example.vynv.final_emo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.vynv.final_emo.fragments.HomeFragment;
import com.example.vynv.final_emo.fragments.HomeFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @AfterViews
    public void initViews(){
        initHome();
    }
    public void initHome(){
        HomeFragment mHomeFragment= HomeFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmHome, mHomeFragment);
        fragmentTransaction.commit();
    }
}
