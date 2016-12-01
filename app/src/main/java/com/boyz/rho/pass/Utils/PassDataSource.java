package com.boyz.rho.pass.Utils;

import android.content.ContentValues;
import android.content.Context;


import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class PassDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String [] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_SITE, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD };

    public PassDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open(String password) throws SQLException{
        database = dbHelper.getWritableDatabase(password);
    }

    public void close () {
        dbHelper.close();
    }

    public Login addPassword(String site, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SITE, site);
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long insertId = database.insert(DatabaseHelper.TABLE_PASS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_PASS, allColumns, DatabaseHelper.COLUMN_ID +  " + " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Login newLogin = new Login(cursor);
        cursor.close();
        return newLogin;
    }

    public void updateSite(String site, String username, String password) {
        ContentValues vals = new ContentValues();
        vals.put("username", username);
        vals.put("password", password);
        database.update(DatabaseHelper.TABLE_PASS, vals, "site=?", new String[]{site});
    }

    public void deletePassword(String site, String username) {
        database.delete(DatabaseHelper.TABLE_PASS,"site=? and username=?", new String[]{site, username});
    }

    public ArrayList<Login> getAllPasswords() {
        ArrayList<Login> logins = new ArrayList<Login>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_PASS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            logins.add(new Login(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return logins;
    }



}
