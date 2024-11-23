package com.example.title.models;

public class UserModel {
    private long ID;
    private String username;
    private String password;
    private long role_id;

    public UserModel(long ID, String username, String password, long role_id) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }
}
