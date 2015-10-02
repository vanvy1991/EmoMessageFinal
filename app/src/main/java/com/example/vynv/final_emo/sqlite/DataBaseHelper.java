package com.example.vynv.final_emo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DB_PATH = "/data/data/message.app.com.emo/databases/";

    public static final String DATABASE_NAME = "mysqlite.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String TABLE_TABS = "tabs";
    private static final String TABLE_IONS_TABS = "icon_tabs";
    private static final String KEY_ID_ICON_TABs = "id";
    private static final String ICON_1_EMO_ICON = "icon1";
    private static final String ICON_2_EMO_ICON = "icon2";
    private static final String TABLE_EMO_ICON = "emo_icon";
    private String TAG = "TAG";

    public DataBaseHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        boolean dbexist = checkDataBase();
        if (dbexist) {
            System.out.println("Database exists");
            openDataBase();
        } else {
            System.out.println("Database doesn't exist");
            createDataBase();
        }
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            System.out.println("Database exists.");
            return;
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }
    }

    public boolean checkDataBase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }

    public void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public Cursor QueryData(String query) throws SQLException {
        return myDataBase.rawQuery(query, null);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log.w(MySQLiteHelper.class.getName(),
        // "Upgrading database from version " + oldVersion + " to "
        // + newVersion + ", which will destroy all old data");
    }
    

    public String[][] _lisAllTabs() {
        // TODO Auto-generated method stub

        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + TABLE_TABS;
            Cursor cursor = db.rawQuery(strSQL, null);
            Log.d(TAG, "Cursor : " + cursor);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];

                    int i = 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }

    public String[][] _list_Icon_Tabs(int id_) {
        // TODO Auto-generated method stub
        Log.d("xxxxxx", "" + id_);
        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = " SELECT * FROM " + TABLE_IONS_TABS + " WHERE " + KEY_ID_ICON_TABs + "= '" + id_ + "'";
            Log.d("cccc", strSQL);
            Cursor cursor = db.rawQuery(strSQL, null);
            Log.d(TAG, "CursorICON : " + cursor);
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];

                    int i = 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            assert cursor != null;
            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }

    public String[][] item_Icon_Tabs(String itemImage1, String itemImage2) {
        // TODO Auto-generated method stub
        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = " SELECT icon3 FROM " + TABLE_EMO_ICON + " WHERE (" + ICON_1_EMO_ICON +
                    "= '" + itemImage1 + "'" + " AND " + ICON_2_EMO_ICON + "= '" + itemImage2 + "')" +
                    " OR (" + ICON_1_EMO_ICON + "= '" + itemImage2 + "'" + " AND " + ICON_2_EMO_ICON + "= '" + itemImage1 + "')";
            Log.d("cccc", strSQL);
            Cursor cursor = db.rawQuery(strSQL, null);
            Log.d(TAG, "CursorICON : " + cursor);
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];

                    int i = 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            assert cursor != null;
            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }
}
