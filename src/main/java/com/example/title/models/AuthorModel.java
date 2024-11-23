package com.example.title.models;

public class AuthorModel {
    private long ID;
    private int age;
    private String nickname;
    private long titleID;
    private long fioId;
    private String titleName;
    private String fio;

    public AuthorModel(long ID, int age, String nickname, long titleID, long fioId, String titleName, String fio) {
        this.ID = ID;
        this.age = age;
        this.nickname = nickname;
        this.titleID = titleID;
        this.fioId = fioId;
        this.titleName = titleName;
        this.fio = fio;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getTitleID() {
        return titleID;
    }

    public void setTitleID(long titleID) {
        this.titleID = titleID;
    }

    public long getFioId() {
        return fioId;
    }

    public void setFioId(long fioId) {
        this.fioId = fioId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
