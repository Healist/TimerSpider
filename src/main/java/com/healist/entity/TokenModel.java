package com.healist.entity;

/**
 * Created by Healist on 2017/1/31.
 */
public class TokenModel {
    private long userId;

    //随机生成uuid
    private String token;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
