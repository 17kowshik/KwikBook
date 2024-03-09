package com.example.kwikbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibraryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "library_database";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MOBILE_NUM = "mobilenum";
    private static final String COLUMN_GENDER = "gender";

    // Books table
    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "_id";
    private static final String COLUMN_BOOK_NAME = "name";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_SYNOPSIS = "synopsis";

    // Lending records table
    private static final String TABLE_LENDING_RECORDS = "lending_records";
    private static final String COLUMN_RECORD_ID = "_id";
    private static final String COLUMN_USER_RECORD_ID = "user_id";
    private static final String COLUMN_BOOK_RECORD_ID = "book_id";
    private static final String COLUMN_LENDING_DATE = "lending_date";
    private static final String COLUMN_RETURN_DATE = "return_date";
    private static final String COLUMN_FINE = "fine";

    public LibraryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_MOBILE_NUM + " TEXT, "
                + COLUMN_GENDER + " TEXT)";
        db.execSQL(createUserTableQuery);

        String createBooksTableQuery = "CREATE TABLE " + TABLE_BOOKS + " ("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BOOK_NAME + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_SYNOPSIS + " TEXT)";
        db.execSQL(createBooksTableQuery);

        String createLendingRecordsTableQuery = "CREATE TABLE " + TABLE_LENDING_RECORDS + " ("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_RECORD_ID + " INTEGER, "
                + COLUMN_BOOK_RECORD_ID + " INTEGER, "
                + COLUMN_LENDING_DATE + " TEXT, "
                + COLUMN_RETURN_DATE + " TEXT, "
                + COLUMN_FINE + " REAL, "
                + "FOREIGN KEY(" + COLUMN_USER_RECORD_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_BOOK_RECORD_ID + ") REFERENCES " + TABLE_BOOKS + "(" + COLUMN_BOOK_ID + "))";
        db.execSQL(createLendingRecordsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LENDING_RECORDS);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public void removeUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
    }

    public long addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public void removeBook(long bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
    }

    public long addLendingRecord(LendingRecord lendingRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public int updateLendingRecord(LendingRecord lendingRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        return 0;
    }

    public void removeLendingRecord(long recordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //
    }

    public void lendBook(long userId, long bookId, String lendingDate, String returnDate) {
        //
    }

    // Method for returning a book
    public void returnBook(long recordId, String returnDate) {
        //
    }

    // Method for generating a fine
    public void generateFine(long recordId, double fineAmount) {
        //
    }

    // Method for users to pay a fine
    public void payFine(long recordId) {
        //
    }

    // Method to get lending reports for a specific user
    public Cursor getLendingReportsForUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //
        return null;
    }
}

