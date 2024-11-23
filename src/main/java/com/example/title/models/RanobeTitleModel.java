package com.example.title.models;

public class RanobeTitleModel {
    private RanobeModel ranobe;
    private TitleModel title;

    public RanobeTitleModel(RanobeModel ranobe, TitleModel title) {
        this.ranobe = ranobe;
        this.title = title;
    }

    public RanobeModel getRanobe() {
        return ranobe;
    }

    public void setRanobe(RanobeModel ranobe) {
        this.ranobe = ranobe;
    }

    public TitleModel getTitle() {
        return title;
    }

    public void setTitle(TitleModel title) {
        this.title = title;
    }
}
