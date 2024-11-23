package com.example.title.models;

public class MangaTitleModel {
    private MangaModel manga;
    private TitleModel title;

    public MangaTitleModel(MangaModel manga, TitleModel title) {
        this.manga = manga;
        this.title = title;
    }

    public MangaModel getManga() {
        return manga;
    }

    public void setManga(MangaModel manga) {
        this.manga = manga;
    }

    public TitleModel getTitle() {
        return title;
    }

    public void setTitle(TitleModel title) {
        this.title = title;
    }
}
