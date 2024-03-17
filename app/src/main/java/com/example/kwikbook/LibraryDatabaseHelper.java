package com.example.kwikbook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    private static final String COLUMN_AVAILABILITY = "availability";

    // Lending records table
    private static final String TABLE_LENDING_RECORDS = "lending_records";
    private static final String COLUMN_RECORD_ID = "_id";
    private static final String COLUMN_USER_RECORD_ID = "user_id";
    private static final String COLUMN_BOOK_RECORD_ID = "book_id";
    private static final String COLUMN_LENDING_DATE = "lending_date";
    private static final String COLUMN_EXPECTED_RETURN_DATE = "expected_return_date";
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
                + COLUMN_SYNOPSIS + " TEXT, "
                + COLUMN_AVAILABILITY + " INTEGER DEFAULT 0)";
        db.execSQL(createBooksTableQuery);

        String createLendingRecordsTableQuery = "CREATE TABLE " + TABLE_LENDING_RECORDS + " ("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_RECORD_ID + " INTEGER, "
                + COLUMN_BOOK_RECORD_ID + " INTEGER, "
                + COLUMN_LENDING_DATE + " TEXT, "
                + COLUMN_EXPECTED_RETURN_DATE + " TEXT, "
                + COLUMN_RETURN_DATE + " TEXT, "
                + COLUMN_FINE + " REAL, "
                + "FOREIGN KEY(" + COLUMN_USER_RECORD_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_BOOK_RECORD_ID + ") REFERENCES " + TABLE_BOOKS + "(" + COLUMN_BOOK_ID + "))";
        db.execSQL(createLendingRecordsTableQuery);


        addAdmin(db);
        addInitialUser(db);
        addSampleBooks(db);
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

    public void addSampleBooks(SQLiteDatabase db) {
        addBook(db, new Book("Introduction to Algorithms", "Thomas H. Cormen et al.", 2009, "A comprehensive guide to algorithms and their analysis, covering fundamental techniques for solving problems efficiently.",0));
        addBook(db, new Book("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin", 2008, "This book offers guidelines for writing clean, maintainable code, focusing on principles and practices for improving software quality.", 0));
        addBook(db, new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma et al.", 1994, "Illustrates classic design patterns in software engineering, providing solutions to common problems in object-oriented design.",0));
        addBook(db, new Book("Operating System Concepts", "Abraham Silberschatz et al.", 2018, "An essential text for understanding operating system principles, covering processes, memory management, file systems, and more.",0));
        addBook(db, new Book("Computer Networking: A Top-Down Approach", "James F. Kurose et al.", 2017, "Introduces networking concepts from a top-down perspective, emphasizing applications, protocols, and the Internet's architecture.",0));
        addBook(db, new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell, Peter Norvig", 2020, "A comprehensive introduction to AI, exploring intelligent agents, search algorithms, machine learning, and natural language processing.",0));
        addBook(db, new Book("Database System Concepts", "Abraham Silberschatz et al.", 2020, "Covers fundamental concepts of database systems, including relational models, query processing, and transaction management.",0));
        addBook(db, new Book("Introduction to the Theory of Computation", "Michael Sipser", 2012, "Explores the theory of computation, formal languages, automata theory, and computational complexity, essential for understanding computation's limits.",0));
        addBook(db, new Book("Computer Organization and Design: The Hardware/Software Interface", "David A. Patterson, John L. Hennessy", 2017, "Examines computer architecture and organization, focusing on the relationship between hardware and software for efficient system design.",0));
        addBook(db, new Book("The Pragmatic Programmer: Your Journey to Mastery", "Andrew Hunt, David Thomas", 2019, "Provides practical advice for software developers, offering tips on productivity, teamwork, and continuous improvement in software development.",0));
        addBook(db, new Book("Microelectronic Circuits", "Adel S. Sedra, Kenneth C. Smith", 2014, "A foundational text on microelectronics, covering semiconductor devices, integrated circuits, and analog/digital circuit analysis and design.",0));
        addBook(db, new Book("Signals and Systems", "Alan V. Oppenheim, Alan S. Willsky", 1996, "Presents principles of signals and systems, including time and frequency domain analysis, Fourier transforms, and system properties, essential for ECE students.",0));
        addBook(db, new Book("Control Systems Engineering", "Norman S. Nise", 2021, "An introductory guide to control systems theory and applications, covering feedback control, system modeling, and stability analysis for ECE and MECH students.",0));
        addBook(db, new Book("Fundamentals of Heat and Mass Transfer", "Theodore L. Bergman et al.", 2011, "Covers principles of heat and mass transfer, including conduction, convection, and radiation, crucial for understanding thermal systems in MECH.",0));
        addBook(db, new Book("Mechanics of Materials", "Russell C. Hibbeler", 2018, "Discusses mechanics of deformable bodies, stress and strain analysis, and structural mechanics, indispensable for designing and analyzing mechanical components.",0));
        addBook(db, new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "A classic novel following the adolescent struggles of Holden Caulfield, exploring themes of identity, alienation, and the transition to adulthood.",0));
        addBook(db, new Book("To Kill a Mockingbird", "Harper Lee", 1960, "Set in the American South, this novel addresses racial injustice and moral growth through the eyes of young Scout Finch, with timeless lessons on empathy and courage.",0));
        addBook(db, new Book("Pride and Prejudice", "Jane Austen", 1813, "A witty and romantic novel depicting societal norms and relationships in 19th-century England, focusing on the independent-minded Elizabeth Bennet and the enigmatic Mr. Darcy.",0));
        addBook(db, new Book("1984", "George Orwell", 1949, "A dystopian masterpiece exploring surveillance, propaganda, and totalitarianism in a future society ruled by the oppressive Party, with themes of resistance and individual freedom.",0));
        addBook(db, new Book("Brave New World", "Aldous Huxley", 1932, "Set in a technologically advanced but morally bankrupt world, this novel contrasts individualism with societal conformity, raising questions about the cost of progress and happiness.",0));
        addBook(db, new Book("Cracking the Coding Interview", "Gayle Laakmann McDowell", 2019, "A comprehensive guide to technical interviews, covering algorithms, data structures, system design, and problem-solving strategies commonly asked in software engineering interviews.",0));
        addBook(db, new Book("Elements of Programming Interviews in Java", "Adnan Aziz et al.", 2016, "Presents a collection of coding problems and solutions, focusing on algorithmic thinking and Java programming techniques for software engineering interview preparation.",0));
        addBook(db, new Book("Programming Interviews Exposed", "John Mongan et al.", 2018, "Provides insights into the software engineering interview process, featuring coding questions, problem-solving strategies, and tips for succeeding in technical interviews.",0));
        addBook(db, new Book("Crack the System Design Interview", "Tushar Roy", 2020, "Guides readers through the system design interview process, covering scalability, availability, and design patterns commonly discussed in software engineering interviews.",0));
        addBook(db, new Book("Dynamic Programming for Coding Interviews", "Meenakshi", 2018, "Focuses on dynamic programming techniques, algorithms, and problem-solving strategies tailored for coding interviews, helping candidates master this critical aspect of technical interviews.",0));
        addBook(db, new Book("Introduction to Psychology", "James W. Kalat", 2019, "Covers the fundamentals of psychology, including biological bases of behavior, cognition, and social interactions, providing insights into human behavior.", 0));
        addBook(db, new Book("Principles of Economics", "N. Gregory Mankiw", 2020, "An introductory text on economics, exploring principles of microeconomics and macroeconomics, markets, and government policies.", 0));
        addBook(db, new Book("Fundamentals of Biology", "Lisa A. Urry et al.", 2016, "Introduces key concepts in biology, including cell structure, genetics, evolution, and ecology, offering a foundation for understanding living organisms.", 0));
        addBook(db, new Book("Principles of Marketing", "Philip T. Kotler et al.", 2017, "Covers principles and practices of marketing, including market research, consumer behavior, branding, and marketing strategies for businesses.", 0));
        addBook(db, new Book("World History: Patterns of Interaction", "Roger B. Beck et al.", 2018, "Explores global history from ancient civilizations to the present day, examining cultural, political, and economic developments worldwide.", 0));
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
        cv.put(COLUMN_AVAILABILITY, book.getAvailability());
        db.insert(TABLE_BOOKS, null, cv);
    }

    public void updateBook(SQLiteDatabase db, Book book) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_NAME, book.getName());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_YEAR, book.getYear());
        cv.put(COLUMN_SYNOPSIS, book.getSynopsis());
        cv.put(COLUMN_AVAILABILITY, book.getAvailability());
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
        cv.put(COLUMN_EXPECTED_RETURN_DATE, lendingRecord.getExpectedReturnDate());
        cv.put(COLUMN_RETURN_DATE, lendingRecord.getReturnDate());
        cv.put(COLUMN_FINE, lendingRecord.getFine());
        db.insert(TABLE_LENDING_RECORDS, null, cv);

    }

    public void updateLendingRecord(SQLiteDatabase db, LendingRecord lendingRecord) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_RECORD_ID, lendingRecord.getUserId());
        cv.put(COLUMN_BOOK_RECORD_ID, lendingRecord.getBookId());
        cv.put(COLUMN_LENDING_DATE, lendingRecord.getLendingDate());
        cv.put(COLUMN_EXPECTED_RETURN_DATE, lendingRecord.getExpectedReturnDate());
        cv.put(COLUMN_RETURN_DATE, lendingRecord.getReturnDate());
        cv.put(COLUMN_FINE, lendingRecord.getFine());
        db.update(TABLE_LENDING_RECORDS, cv, COLUMN_RECORD_ID + "=?", new String[]{String.valueOf(lendingRecord.getId())});


    }

    public void removeLendingRecord(SQLiteDatabase db, long recordId) {
        db.delete(TABLE_LENDING_RECORDS, COLUMN_RECORD_ID + "=?", new String[]{String.valueOf(recordId)});
    }

    public void lendBook(SQLiteDatabase db, Context context, long userId, long bookId, String lendingDate, String expectedReturnDate) {
        try {
            if (isAvailable(db, context, bookId)) { // Check if the book is available
                // Proceed with lending
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_USER_RECORD_ID, userId);
                cv.put(COLUMN_BOOK_RECORD_ID, bookId);
                cv.put(COLUMN_LENDING_DATE, lendingDate);
                cv.put(COLUMN_EXPECTED_RETURN_DATE, expectedReturnDate);
                cv.put(COLUMN_RETURN_DATE, ""); // Initially empty, indicating not returned yet
                cv.put(COLUMN_FINE, 0); // Initially no fine

                // Insert lending record
                long recordId = db.insert(TABLE_LENDING_RECORDS, null, cv);

                // Update book availability
                ContentValues bookCV = new ContentValues();
                bookCV.put(COLUMN_AVAILABILITY, 1); // Set availability to not available
                db.update(TABLE_BOOKS, bookCV, COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(bookId)});
            }
        } catch (Exception e) {
            // Error lending book
            Toast.makeText(context, "Error lending book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    // Method to check if a book is available
    public boolean isAvailable(SQLiteDatabase db, Context context, long bookId) {
        Cursor cursor = null;
        try {
            // Check if the book is available for lending
            cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_AVAILABILITY}, COLUMN_BOOK_ID + "=?",
                    new String[]{String.valueOf(bookId)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") int availability = cursor.getInt(cursor.getColumnIndex(COLUMN_AVAILABILITY));
                if (availability == 0) {
                    return true; // Book is available
                } else {
                    // Book is not available
                    Toast.makeText(context, "Book is not available for lending", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // Default to false in case of any exception or if book not found
        return false;
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
    @SuppressLint("Range")
    public int getUserID(SQLiteDatabase db, String username) {
        Cursor cursor = null;
        int id = -1;

        try {
            cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID}, COLUMN_USERNAME + "=?",
                    new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return id;
    }

    @SuppressLint("Range")
    public String getBookName(SQLiteDatabase db, long bookID) {
        Cursor cursor = null;
        String bookName = null;

        try {
            cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_BOOK_NAME}, COLUMN_BOOK_ID + "=?",
                    new String[]{String.valueOf(bookID)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                bookName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return bookName;
    }
}

