package com.boyz.rho.pass.Utils;


import net.sqlcipher.Cursor;

public class Login {

    private long id;
    private String site;
    private String username;
    private String password;

    public Login(Cursor cursor) {
        this.id = cursor.getLong(0);
        this.site = cursor.getString(1);
        this.username = cursor.getString(2);
        this.password = cursor.getString(3);
    }

    public long getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
