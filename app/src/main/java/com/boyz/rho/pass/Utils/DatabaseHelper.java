package com.boyz.rho.pass.Utils;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by rho on 11/14/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "pass.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PASS = "pass";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SITE = "site";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_DATABASE = "create table " + TABLE_PASS + "( " + COLUMN_ID +
            " integer primary key autoincrement, " + COLUMN_SITE + " text not null, " +
            COLUMN_USERNAME + " text not null, " + COLUMN_PASSWORD + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
       database.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //do nothing
    }


}
