package com.example.title.models;

public class AuthorTitleFio {
    private AuthorModel author;
    private TitleModel title;
    private FioModel fio;

    public AuthorTitleFio(AuthorModel author, TitleModel title, FioModel fio) {
        this.author = author;
        this.title = title;
        this.fio = fio;
    }

    public AuthorModel getAuthor() {
        return author;
    }

    public void setAuthor(AuthorModel author) {
        this.author = author;
    }

    public TitleModel getTitle() {
        return title;
    }

    public void setTitle(TitleModel title) {
        this.title = title;
    }

    public FioModel getFio() {
        return fio;
    }

    public void setFio(FioModel fio) {
        this.fio = fio;
    }
}
