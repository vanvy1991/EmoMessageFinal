package com.example.vynv.final_emo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vynv.final_emo.fragments.HomeFragment;
import com.example.vynv.final_emo.fragments.HomeFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.vynv.final_emo.common.Util.openFileSDCard;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {
    String text = "";
    ArrayList<String> itemRecent;
    private static int ADD_CODE = 111;
    private static int GET_CODE = 222;
    String[] dataIconRecent;

    @AfterViews
    public void initViews() {
        initHome();
    }

    public void initHome() {
        HomeFragment mHomeFragment = HomeFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmHome, mHomeFragment);
        fragmentTransaction.commit();
    }

    public ArrayList<String> createFile(int CODE, String icon) {
        itemRecent = new ArrayList<>();
        ArrayList<String> itemTMP = new ArrayList<>();
        String arrayRecent = openFileSDCard();
        if (arrayRecent != null) {
            String[] dataIconRecent = arrayRecent.replace("$", " ").split(" ");
            Log.d("x01", "" + Arrays.toString(dataIconRecent));
            if (CODE == GET_CODE) {
                for (String item : dataIconRecent) {
                    if (itemRecent.size() < 9) {
                        if (itemTMP != null) {
                            for (String itemIcon : itemTMP) {
                                if (!itemIcon.equals(item)) {
                                    itemRecent.add(item);
                                    itemTMP.add(item);
                                }
                            }
                        } else {
                            itemTMP.add(item);
                            itemRecent.add(item);
                        }
                    } else {
                        return itemRecent;
                    }

                }
            }
            if (CODE == ADD_CODE) {
                String saveIconRecent = icon + "$" + arrayRecent;
                try {
                    File recentFile = new File("/sdcard/EmoIcon/mysdfile.txt");
                    if (recentFile.exists()) {
                        FileWriter writer = new FileWriter(recentFile);
                        writer.write(saveIconRecent);
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
//        if (arrayRecent != null) {
//            for (String item : arrayRecent) {
//                String str= item.replaceAll("$", " ");
//                dataIconRecent = str.split(" ");
//                Collections.addAll(itemRecent, dataIconRecent[0].replaceAll("&", " "));
//            }
//            Log.d("xxxxx2",""+ Arrays.toString(dataIconRecent) +"---"+arrayRecent);
//            if (arrayRecent != null) {
//                arrayRecent.add(String.valueOf(resourceID));
//
//                try {
//                    File gpxfile = new File("/sdcard/EmoIcon/", fileName);
//                    if (gpxfile.exists()) {
//                        FileWriter writer = new FileWriter(gpxfile);
//                        Log.d("xxx111", "Da vao" + arrayRecent);
//                        for (int i = 0; i <= arrayRecent.size(); i++) {
//                            writer.write("ddddddd" + "$");
//                        }
//                        writer.flush();
//                        writer.close();
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String text=resourceID+"\n";
//        File pathFile = new File("/sdcard/EmoIcon/");
//        if(!pathFile.exists()){
//            pathFile.mkdir();
//        }
//
    }

}
