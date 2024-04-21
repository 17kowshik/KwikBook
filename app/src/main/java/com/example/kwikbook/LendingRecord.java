package com.example.kwikbook;

public class LendingRecord {

    private long id;
    private long userId;
    private long bookId;
    private String lendingDate;
    private String expectedReturnDate;
    private String returnDate;
    private double fine;
    private int fee_paid;

    public LendingRecord() {}

    public LendingRecord(long userId, long bookId, String lendingDate, String expectedReturnDate, String returnDate, double fine, int fee_paid) {
        this.userId = userId;
        this.bookId = bookId;
        this.lendingDate = lendingDate;
        this.expectedReturnDate = expectedReturnDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.fee_paid = fee_paid;
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

    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(String expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public int getFee_paid() {
        return fee_paid;
    }

    public void setFee_paid(int fee_paid) {
        this.fee_paid = fee_paid;
    }
}

