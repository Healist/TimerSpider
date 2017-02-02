package com.healist.entity;

/**
 * Created by Healist on 2017/1/31.
 */
public class User {

    private String username;
    private String password;
    private long id;
    private String email;
    private String sex;

    public  User() {}

    public User(String username, String password, String email, String sex) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
