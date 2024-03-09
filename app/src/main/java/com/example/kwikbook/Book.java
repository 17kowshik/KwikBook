package com.example.kwikbook;

public class Book {

    private long id;
    private String name;
    private String author;
    private int year;
    private String synopsis;

    public Book() {}

    public Book(String name, String author, int year, String synopsis) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.synopsis = synopsis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
