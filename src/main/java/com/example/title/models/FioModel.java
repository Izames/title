package com.example.title.models;

public class FioModel {
    private long ID;
    private String name;
    private String surname;
    private String lastname;

    public FioModel(long ID, String name, String surname, String lastname) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
