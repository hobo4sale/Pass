package com.boyz.rho.pass.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class PassDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String [] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_COMPANY, DatabaseHelper.COLUMN_PASSWORD };

    public PassDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close () {
        dbHelper.close();
    }

    public Password addPassword(String company, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COMPANY, company);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long insertId = database.insert(DatabaseHelper.TABLE_PASS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_PASS, allColumns, DatabaseHelper.COLUMN_ID +  " + " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Password newPassword = new Password(cursor);
        cursor.close();
        return newPassword;
    }


    public ArrayList<Password> getAllPasswords() {
        ArrayList<Password> passwords = new ArrayList<Password>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_PASS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            passwords.add(new Password(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return passwords;
    }



}
