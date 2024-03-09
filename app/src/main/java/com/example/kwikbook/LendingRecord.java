package com.example.kwikbook;

public class LendingRecord {

    private long id;
    private long userId;
    private long bookId;
    private String lendingDate;
    private String returnDate;
    private double fine;

    public LendingRecord() {}

    public LendingRecord(long userId, long bookId, String lendingDate, String returnDate, double fine) {
        this.userId = userId;
        this.bookId = bookId;
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(String lendingDate) {
        this.lendingDate = lendingDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}

