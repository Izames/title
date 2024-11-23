package com.example.title.models;

public class AnimeModel {
    private long ID;
    private String name;
    private int seasons;
    private int episodes;
    private long titleID;
    private String titleName;

    public AnimeModel(long ID, String name, int seasons, int episodes, long titleID, String titleName) {
        this.ID = ID;
        this.name = name;
        this.seasons = seasons;
        this.episodes = episodes;
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

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        seasons = seasons;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
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
