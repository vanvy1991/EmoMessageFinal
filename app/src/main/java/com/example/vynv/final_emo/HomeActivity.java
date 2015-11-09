package com.example.vynv.final_emo;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vynv.final_emo.fragments.HomeFragment;
import com.example.vynv.final_emo.fragments.HomeFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.example.vynv.final_emo.common.Util.openFileSDCard;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {
    String text = "";
    private static String PATH_FILE = "mysdfile.log";
    private static String PATH_FILE_ICONS_TAB = "icons_tab.txt";
    ArrayList<String> itemRecent;
    ArrayList<String> itemTMP;
    private static int ADD_CODE = 111;
    private static int GET_CODE = 222;
File iconTabFile;
    SharedPreferences firstTime;

    @AfterViews
    public void initViews() {
        firstTime=this.getSharedPreferences("first_time",0);
        if(firstTime.getBoolean("first_time",true)) {
            createFile(ADD_CODE, 0);
            Log.d("xxxx12","cccc");
            firstTime.edit().putBoolean("first_time",false).commit();
        }
        initHome();
    }

    public void initHome() {
        HomeFragment mHomeFragment = HomeFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmHome, mHomeFragment);
        fragmentTransaction.commit();
    }

    public ArrayList<String> createFile(int CODE, int icon) {
        try {
            iconTabFile = new File("/sdcard/EmoIcon/" + PATH_FILE_ICONS_TAB);
            if(!iconTabFile.exists()) {
                InputStream myInput = this.getAssets().open(PATH_FILE_ICONS_TAB);
                OutputStream myOutput = new FileOutputStream("/sdcard/EmoIcon/" + PATH_FILE_ICONS_TAB);
                if (iconTabFile.length() <= 0) {
                    iconTabFile.createNewFile();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = myInput.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }
                    // Close the streams
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                }
                Log.d("xxx",""+iconTabFile.length());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        itemRecent = new ArrayList<>();
        itemTMP = new ArrayList<>();
        String arrayRecent = openFileSDCard(PATH_FILE);
        boolean checkSame = false;
        if (arrayRecent != null) {
            String[] dataIconRecent = arrayRecent.replace("$", " ").split(" ");
            if (CODE == GET_CODE) {
                for (int item = 0; item <= dataIconRecent.length - 1; item++) {
                    if(!dataIconRecent[item].equals("0")) {
                        if (itemRecent.size() < 20) {
                            for (String items : itemRecent) {
                                if (items != null) {
                                    if (dataIconRecent[item].equals(items)) {
                                        checkSame = true;
                                        break;
                                    } else {
                                        checkSame = false;
                                    }
                                }
                            }
                            if (!checkSame) {
                                itemRecent.add(dataIconRecent[item]);
                            }
                        } else {
                            break;
                        }
                    }
                }
                return itemRecent;
            }
            if (CODE == ADD_CODE) {
                if(icon>0) {
                    String saveIconRecent = icon + "$" + arrayRecent;
                    Log.d("xxaaa", "" + saveIconRecent);
                    try {
                        File recentFile = new File("/sdcard/EmoIcon/" + PATH_FILE);
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
        } else {
            if (icon > 0) {
                Log.d("xxxxx", "vao day" + icon);
                String saveIconRecent = String.valueOf(icon);
                try {
                    File recentFile = new File("/sdcard/EmoIcon/" + PATH_FILE);
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
    }
}
