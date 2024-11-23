package com.example.title.models;

public class AnimeTitleModel {
    private AnimeModel animes;
    private TitleModel titles;

    public AnimeTitleModel(AnimeModel animes, TitleModel titles) {
        this.animes = animes;
        this.titles = titles;
    }

    public AnimeModel getAnimes() {
        return animes;
    }

    public void setAnimes(AnimeModel animes) {
        this.animes = animes;
    }

    public TitleModel getTitles() {
        return titles;
    }

    public void setTitles(TitleModel titles) {
        this.titles = titles;
    }
}
