package com.example.hiroyuki.googledlistforandroid.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hiroyuki on 2016/05/08.
 */
public class APIResult {

    private String status;
    private String token;
    private String loginOk;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginOk() {
        return loginOk;
    }

    public void setLoginOk(String loginOk) {
        this.loginOk = loginOk;
    }

}
