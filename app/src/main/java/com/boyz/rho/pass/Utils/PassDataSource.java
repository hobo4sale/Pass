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

    public Password addPassword(String site, String username,  String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SITE, site);
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long insertId = database.insert(DatabaseHelper.TABLE_PASS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_PASS, allColumns, DatabaseHelper.COLUMN_ID +  " + " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Password newPassword = new Password(cursor);
        cursor.close();
        return newPassword;
    }

    public void updateUsername(String site, String newUsername, String oldUsername) {
        String updateUsernameSQL = "UPDATE " + DatabaseHelper.TABLE_PASS + " SET username=" + newUsername + " WHERE site=" + site + " AND username=" + oldUsername + ";";
        database.execSQL(updateUsernameSQL);

    }

    public void updatePassword(String site, String username, String password) {
        String updatePasswordSQL = "UPDATE " + DatabaseHelper.TABLE_PASS + " SET password=" + password + " WHERE site=" + site + " username=" + username + ";";
        database.execSQL(updatePasswordSQL);
    }

    public void deletePassword(String site, String username) {
        String deleteSQL = "DELETE FROM " + DatabaseHelper.TABLE_PASS + " WHERE site=" + site + " AND username=" + username + ";";
        database.execSQL(deleteSQL);
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
