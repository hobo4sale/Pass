package com.boyz.rho.pass.Utils;


import net.sqlcipher.Cursor;

public class Password {

    private long id;
    private String company;
    private String password;

    public Password(Cursor cursor) {
        this.id = cursor.getLong(0);
        this.company = cursor.getString(1);
        this.password = cursor.getString(2);
    }

}
