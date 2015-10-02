package com.example.vynv.final_emo.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nguyenvantuan on 7/30/15.
 */
public class Util {
    private static int ICONS_TAB = 100;
    private static int TEXT_TAB = 200;
    private static int ID_TAB = 300;
    private static int RESULT_EMO = 400;
    static String line;
    public static String resultEmoji;
    public static ArrayList<String> resultTabEmo;

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
        BufferedReader bReader;
        ArrayList<String> values;
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
                        Collections.addAll(resultTabEmo, dataTab[0]);
                    }
                }

            }
            return resultTabEmo;
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
                    break;
                }
            }
            return resultEmoji;
        }
        return null;
    }
}
