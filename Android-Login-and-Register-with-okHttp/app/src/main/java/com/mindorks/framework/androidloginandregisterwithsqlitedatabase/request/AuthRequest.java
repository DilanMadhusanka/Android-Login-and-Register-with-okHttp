package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.request;

public class AuthRequest {

    String email;
    String password;

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
