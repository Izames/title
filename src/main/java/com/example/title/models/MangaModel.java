package com.example.title.models;

public class MangaModel {
    private long ID;
    private String name;
    private int chapters;
    private long titleID;
    private String titleName;

    public MangaModel(long ID, String name, int chapters, long titleID, String titleName) {
        this.ID = ID;
        this.name = name;
        this.chapters = chapters;
        this.titleID = titleID;
        this.titleName = titleName;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public long getTitleID() {
        return titleID;
    }

    public void setTitleID(long titleID) {
        this.titleID = titleID;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
