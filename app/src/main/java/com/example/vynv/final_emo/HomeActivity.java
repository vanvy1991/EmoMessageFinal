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

import static com.example.vynv.final_emo.common.Util.openFileSDCard;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {
    String text = "";
    ArrayList<String> itemRecent;
    ArrayList<String> itemTMP;
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
        itemTMP = new ArrayList<>();
        String arrayRecent = openFileSDCard();
        boolean checkSame = false;
        if (arrayRecent != null) {
            String[] dataIconRecent = arrayRecent.replace("$", " ").split(" ");
            if (CODE == GET_CODE) {
                for (int item=0 ;item<= dataIconRecent.length-1;item++) {
                    if(itemRecent.size()<9) {
                        for(String items:itemRecent){
                            if(dataIconRecent[item].equals(items)){
                                checkSame=true;
                                Log.d("xxxx11",""+checkSame);
                                break;
//                                itemRecent.add(dataIconRecent[item]);
                            }
                            else{
                                checkSame=false;
                                Log.d("xxxx12",""+checkSame);
                            }
                        }
                        if(!checkSame){
                            Log.d("xxxx13",""+checkSame);
                            itemRecent.add(dataIconRecent[item]);
                        }
//                        for (String items : itemRecent) {
//                            if (!items.equals(dataIconRecent[item])) {
//                                Log.d("xxxx03", "item:" + item + "----" + itemRecent);
//                            }
//                        }
                    }
                    else{
                        break;
                    }
                }
                return itemRecent;
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
