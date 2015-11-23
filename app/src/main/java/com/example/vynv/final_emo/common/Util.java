package com.example.vynv.final_emo.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nguyenvantuan on 7/30/15.
 */
public class Util {
    private static int ICONS_TAB = 100;
    private static int TEXT_TAB = 200;
    private static int ID_TAB = 300;
    private static int RECENT_TAB = 500;
    private static int RESULT_EMO = 400;
    private static int SHOW = 111;
    private static int HIDE = 222;
    static BufferedReader bReader;
    static ArrayList<String> values;
    static String line;
    static String PATH = "/sdcard/EmoIcon/";
    public static String resultEmoji;
    public static ArrayList<String> resultTabEmo;
    static FileOutputStream fos;
    static OutputStreamWriter myOutWriter;
    static FileWriter writer;

    public static int getHeightScreen(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getWidthScreen(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int convertDPToPixels(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static ArrayList<String> setResultEmo(Context mContext, String fileName) {

        try {
            bReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(fileName)));
            values = new ArrayList<>();
            line = bReader.readLine();
            while (line != null) {
                values.add(line);
                line = bReader.readLine();
            }
            bReader.close();
            return values;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> setIdTab(String fileName, int idTab){
        ArrayList<String> arr=new ArrayList<>();
        File tabFile = new File(PATH+fileName);
        if (tabFile.exists()) {
            ArrayList<String> arrayResultTab = showDataFile(PATH+fileName);
            if (arrayResultTab != null) {
                for(String v : arrayResultTab){
                    String[] dataTab = v.split(" ");
                    if (dataTab[1].equals(String.valueOf(idTab))) {
                        if (dataTab[2].equals(String.valueOf(1))) {
                            Collections.addAll(arr, dataTab[0]);
                        }
                    }
                }
            }
        }
        return  arr;
    }
    public static ArrayList<String> setTabEmo(Context mContext, String fileName, int param, int idTab) {
        resultTabEmo = new ArrayList<>();
        ArrayList<String> arrayResultTab = setResultEmo(mContext, fileName);
        if (arrayResultTab != null) {
            for (String v : arrayResultTab) {
                String[] dataTab = v.split(" ");
                if (param == ICONS_TAB) {
                    Collections.addAll(resultTabEmo, dataTab[0]);
                }
                if (param == TEXT_TAB) {
                    Collections.addAll(resultTabEmo, dataTab[1].replaceAll("&", " "));
                }
                if (param == ID_TAB) {
                    if (dataTab[1].equals(String.valueOf(idTab))) {
                        if (dataTab[2].equals(String.valueOf(1))) {
                            Collections.addAll(resultTabEmo, dataTab[0]);
                        }
                    }
                }
                if (param == RECENT_TAB) {
                    Collections.addAll(resultTabEmo, dataTab[0]);
                }

            }
            return resultTabEmo;
        }
        return null;
    }

    public static ArrayList<String> showDataFile(String fileName) {
        try {
            bReader = new BufferedReader(new FileReader(fileName));
            values = new ArrayList<>();
            line = bReader.readLine();
            while (line != null) {
                values.add(line);
                line = bReader.readLine();
            }
            bReader.close();
            return values;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateShowHide(Context mContext, String image, String fileName, int showHide) {
//            writer = new FileWriter(String.valueOf(bReader));
//            Log.d("xxxx1",""+writer.getEncoding());
        int tmp = 0;
        File recentFile = new File("/sdcard/EmoIcon/icons_tab.txt");
        if (recentFile.exists()) {
            ArrayList<String> arrayResultEmo = showDataFile("/sdcard/EmoIcon/icons_tab.txt");
            if (arrayResultEmo != null) {
                for (String v : arrayResultEmo) {
                    String[] data = v.split(" ");
                    int index = data[2].indexOf(image);
                    tmp++;
                    if (data[0].equals(image)) {

                            if (showHide == 0) {
                                data[2] = String.valueOf(1);
                            } else {
                                if(data[3].equals("0")){
                                    Log.d("xxxx12", "" + index);
                                    data[3] = String.valueOf(1);
                                }
                            }
                            arrayResultEmo.set((tmp - 1), data[0] + " " + data[1] + " " + data[2] + " " + data[3]);
                            getIconTab(arrayResultEmo);
                            Log.d("xxx2", "" + arrayResultEmo);
                            break;
                        }
                }
            }
        }
    }

    public static void getIconTab(ArrayList<String> arrayList) {
        File getIconFile = new File("/sdcard/EmoIcon/icons_tab.txt");
        if (getIconFile.exists()) {
            try {
                FileOutputStream fOut = new FileOutputStream(getIconFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                for (String v : arrayList) {
                    myOutWriter.append(v+"\n");
                }
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static int getResultShowHide(Context mContext, String image, String fileName) {
        int showHide;

        ArrayList<String> arrayResultEmo = showDataFile(PATH+fileName);
        if (arrayResultEmo != null) {
            for (String v : arrayResultEmo) {
                String[] data = v.split(" ");
                if (data[0].equals(image)) {
                    showHide = Integer.parseInt(data[2]);
                    return showHide;
                }
            }
        }
        return 1;
    }

    public static String showHideNewGrid(String nameImage){
        ArrayList<String> arrayResultEmo = showDataFile("/sdcard/EmoIcon/icons_tab.txt");
        ArrayList<String> result = new ArrayList<>();
        String[] data;
        if (arrayResultEmo != null) {
            for (String v : arrayResultEmo) {
                data = v.split(" ");
                if (data[0].equals(nameImage)) {
                    Log.d("xxxx", "" + nameImage);
                    return data[3];
                }
            }

        }
        return null;
    }
    public static String getResultEmoji(Context mContext, String param1, String param2, String fileName) {

        ArrayList<String> arrayResultEmo = setResultEmo(mContext, fileName);
        if (arrayResultEmo != null) {
            for (String v : arrayResultEmo) {
                String[] data = v.split(" ");
                if ((data[0].equals(param1) && data[1].equals(param2)) || (data[1].equals(param1) && data[0].equals(param2))) {
                    resultEmoji = data[(data.length - 1)];
                    return resultEmoji;
                }
            }
        }
        return null;
    }


    public static String openFileSDCard(String pathFile) {
        try {
            File fileFolder = new File(PATH);

            File myFile = new File(PATH + pathFile);
            if (fileFolder.exists() && myFile.exists()) {
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = myReader.readLine();
                return aDataRow;
            } else {
                fileFolder.mkdir();
                myFile.createNewFile();
                Log.d("xxx122", "da vao");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha256(String string) {
        try {
            MessageDigest sss = MessageDigest.getInstance("SHA-256");
            sss.update(string.getBytes());
            byte byteData[] = sss.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (byte tmp : byteData) {
                sb.append(Integer.toString((tmp & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
