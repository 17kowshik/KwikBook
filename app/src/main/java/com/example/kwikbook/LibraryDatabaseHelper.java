package com.example.kwikbook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                + COLUMN_MOBILE_NUM + " TEXT)";
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


        addAdmin(db);
        addInitialUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LENDING_RECORDS);
        onCreate(db);
    }
    private void addInitialUser(SQLiteDatabase db) {
        User firstUser = new User("test", "kb", "KwikBook", "987543210");
        addUser(db, firstUser);
    }

    private void addAdmin(SQLiteDatabase db){
        User admin = new User("admin", "adminAccess", "Administrator", "999999999");
        addUser(db, admin);
    }

    public void addUser(SQLiteDatabase db, User user) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_NAME, user.getName());
        cv.put(COLUMN_MOBILE_NUM, user.getMobileNum());
        db.insert(TABLE_USERS, null, cv);
    }

    public void updateUser(SQLiteDatabase db, User user) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_NAME, user.getName());
        cv.put(COLUMN_MOBILE_NUM, user.getMobileNum());
        db.update(TABLE_USERS, cv, COLUMN_USER_ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public void removeUser(SQLiteDatabase db, long userId) {
        db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    public void addBook(SQLiteDatabase db, Book book) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_NAME, book.getName());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_YEAR, book.getYear());
        cv.put(COLUMN_SYNOPSIS, book.getSynopsis());
        db.insert(TABLE_BOOKS, null, cv);
    }

    public void updateBook(SQLiteDatabase db, Book book) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_NAME, book.getName());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_YEAR, book.getYear());
        cv.put(COLUMN_SYNOPSIS, book.getSynopsis());
        db.update(TABLE_BOOKS, cv, COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(book.getId())});

    }

    public void removeBook(SQLiteDatabase db, long bookId) {
        db.delete(TABLE_BOOKS, COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(bookId)});
    }

    public void addLendingRecord(SQLiteDatabase db, LendingRecord lendingRecord) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_RECORD_ID, lendingRecord.getUserId());
        cv.put(COLUMN_BOOK_RECORD_ID, lendingRecord.getBookId());
        cv.put(COLUMN_LENDING_DATE, lendingRecord.getLendingDate());
        cv.put(COLUMN_RETURN_DATE, lendingRecord.getReturnDate());
        cv.put(COLUMN_FINE, lendingRecord.getFine());
        db.insert(TABLE_LENDING_RECORDS, null, cv);

    }

    public void updateLendingRecord(SQLiteDatabase db, LendingRecord lendingRecord) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_RECORD_ID, lendingRecord.getUserId());
        cv.put(COLUMN_BOOK_RECORD_ID, lendingRecord.getBookId());
        cv.put(COLUMN_LENDING_DATE, lendingRecord.getLendingDate());
        cv.put(COLUMN_RETURN_DATE, lendingRecord.getReturnDate());
        cv.put(COLUMN_FINE, lendingRecord.getFine());
        db.update(TABLE_LENDING_RECORDS, cv, COLUMN_RECORD_ID + "=?", new String[]{String.valueOf(lendingRecord.getId())});


    }

    public void removeLendingRecord(SQLiteDatabase db, long recordId) {
        db.delete(TABLE_LENDING_RECORDS, COLUMN_RECORD_ID + "=?", new String[]{String.valueOf(recordId)});
    }

    public void lendBook(long userId, long bookId, String lendingDate, String returnDate) {
        //
    }

    // Method for returning a book
    public void returnBook(SQLiteDatabase db, long recordId, String returnDate) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RETURN_DATE, returnDate);
        db.update(TABLE_LENDING_RECORDS, cv, COLUMN_RECORD_ID + "=?", new String[]{String.valueOf(recordId)});
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

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String TABLE_USERS = "users";
        final String COLUMN_USERNAME = "username";
        final String COLUMN_PASSWORD = "password";
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);

        boolean isAuthenticated = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
            db.close();
        }
        return isAuthenticated;
    }

    public boolean doesUserExist(SQLiteDatabase db, String email) {
        Cursor cursor = null;

        try {
            // Query to check if the user with the given email exists
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
            cursor = db.rawQuery(query, new String[]{email});
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @SuppressLint("Range")
    public String getName(SQLiteDatabase db, String username) {
        Cursor cursor = null;
        String name = null;

        try {
            cursor = db.query(TABLE_USERS, new String[]{COLUMN_NAME}, COLUMN_USERNAME + "=?",
                    new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return name;
    }
}

