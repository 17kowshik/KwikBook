package com.example.kwikbook;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class updateDB_Activity extends AppCompatActivity {
    ImageView addUser, addBook, addLendingRecord;
    ImageView updateUser, updateBook, updateLendingRecord;
    ImageView removeUser, removeBook, removeLendingRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_db);
        addUser = findViewById(R.id.imageView1);
        addBook = findViewById(R.id.imageView4);
        addLendingRecord = findViewById(R.id.imageView7);
        updateUser = findViewById(R.id.imageView2);
        updateBook = findViewById(R.id.imageView5);
        updateLendingRecord = findViewById(R.id.imageView8);
        removeUser = findViewById(R.id.imageView3);
        removeBook = findViewById(R.id.imageView6);
        removeLendingRecord = findViewById(R.id.imageView9);

        addUser.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Add User");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText usernameEditText = new EditText(updateDB_Activity.this);
            usernameEditText.setHint("Username");
            layout.addView(usernameEditText);

            final EditText passwordEditText = new EditText(updateDB_Activity.this);
            passwordEditText.setHint("Password");
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            layout.addView(passwordEditText);

            final EditText nameEditText = new EditText(updateDB_Activity.this);
            nameEditText.setHint("Name");
            layout.addView(nameEditText);

            final EditText mobileNumEditText = new EditText(updateDB_Activity.this);
            mobileNumEditText.setHint("Mobile Number");
            mobileNumEditText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
            layout.addView(mobileNumEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Add user to the database
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String mobileNum = mobileNumEditText.getText().toString().trim();
                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && !mobileNum.isEmpty()) {
                    User newUser = new User(username,password,name,mobileNum);
                    ldbHelper.addUser(db,newUser);
                    Toast.makeText(updateDB_Activity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        addBook.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Add Book");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText bookNameEditText = new EditText(updateDB_Activity.this);
            bookNameEditText.setHint("Book Name");
            layout.addView(bookNameEditText);

            final EditText authorEditText = new EditText(updateDB_Activity.this);
            authorEditText.setHint("Author Name");
            layout.addView(authorEditText);

            final EditText yearEditText = new EditText(updateDB_Activity.this);
            yearEditText.setHint("Year");
            layout.addView(yearEditText);

            final EditText synopsisEditText = new EditText(updateDB_Activity.this);
            synopsisEditText.setHint("Synopsis");
            layout.addView(synopsisEditText);

            final EditText availabilityEditText = new EditText(updateDB_Activity.this);
            availabilityEditText.setHint("Availability");
            layout.addView(availabilityEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Add user to the database
                String bookname = bookNameEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();
                int year = Integer.parseInt(yearEditText.getText().toString().trim());
                String synopsis = synopsisEditText.getText().toString().trim();
                int availability = Integer.parseInt(availabilityEditText.getText().toString().trim());
                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!bookname.isEmpty() && !author.isEmpty() && !String.valueOf(year).isEmpty() && !synopsis.isEmpty() &&  !String.valueOf(availability).isEmpty()) {
                    Book newBook = new Book(bookname,author,year,synopsis,availability);
                    ldbHelper.addBook(db,newBook);
                    Toast.makeText(updateDB_Activity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        addLendingRecord.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Add Lending Record");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText userRecordIDEditText = new EditText(updateDB_Activity.this);
            userRecordIDEditText.setHint("User Record ID");
            layout.addView(userRecordIDEditText);

            final EditText bookRecordIDEditText = new EditText(updateDB_Activity.this);
            bookRecordIDEditText.setHint("Book Record ID");
            layout.addView(bookRecordIDEditText);

            final EditText lendingDateEditText = new EditText(updateDB_Activity.this);
            lendingDateEditText.setHint("Lending Date (YYYY-MM-DD)");
            layout.addView(lendingDateEditText);

            final EditText expectedReturnDateEditText = new EditText(updateDB_Activity.this);
            expectedReturnDateEditText.setHint("Expected Return Date (YYYY-MM-DD)");
            layout.addView(expectedReturnDateEditText);

            final EditText returnDateEditText = new EditText(updateDB_Activity.this);
            returnDateEditText.setHint("Return Date (YYYY-MM-DD)");
            layout.addView(returnDateEditText);

            final EditText fineEditText = new EditText(updateDB_Activity.this);
            fineEditText.setHint("Fine");
            layout.addView(fineEditText);

            final EditText feePaidEditText = new EditText(updateDB_Activity.this);
            feePaidEditText.setHint("Fee Paid (1 for Yes, 0 for No)");
            layout.addView(feePaidEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Add lending record to the database
                String userRecordID = userRecordIDEditText.getText().toString().trim();
                String bookRecordID = bookRecordIDEditText.getText().toString().trim();
                String lendingDate = lendingDateEditText.getText().toString().trim();
                String expectedReturnDate = expectedReturnDateEditText.getText().toString().trim();
                String returnDate = returnDateEditText.getText().toString().trim();
                String fine = fineEditText.getText().toString().trim();
                String feePaid = feePaidEditText.getText().toString().trim();

                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!userRecordID.isEmpty() && !bookRecordID.isEmpty() && !lendingDate.isEmpty() && !expectedReturnDate.isEmpty() && !returnDate.isEmpty() && !fine.isEmpty() && !feePaid.isEmpty()) {
                    // Create lending record instance
                    LendingRecord newRecord = new LendingRecord(Integer.parseInt(userRecordID), Integer.parseInt(bookRecordID), lendingDate, expectedReturnDate, returnDate, Double.parseDouble(fine), Integer.parseInt(feePaid));
                    ldbHelper.addLendingRecord(db, newRecord);
                    Toast.makeText(updateDB_Activity.this, "Lending Record Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        updateUser.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Update User");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText usernameEditText = new EditText(updateDB_Activity.this);
            usernameEditText.setHint("Username");
            layout.addView(usernameEditText);

            final EditText passwordEditText = new EditText(updateDB_Activity.this);
            passwordEditText.setHint("Password");
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            layout.addView(passwordEditText);

            final EditText nameEditText = new EditText(updateDB_Activity.this);
            nameEditText.setHint("Name");
            layout.addView(nameEditText);

            final EditText mobileNumEditText = new EditText(updateDB_Activity.this);
            mobileNumEditText.setHint("Mobile Number");
            mobileNumEditText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
            layout.addView(mobileNumEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Update user to the database
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String mobileNum = mobileNumEditText.getText().toString().trim();
                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && !mobileNum.isEmpty()) {
                    User newUser = new User(username,password,name,mobileNum);
                    ldbHelper.updateUser(db,newUser);
                    Toast.makeText(updateDB_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        updateUser.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Update User");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText usernameEditText = new EditText(updateDB_Activity.this);
            usernameEditText.setHint("Username");
            layout.addView(usernameEditText);

            final EditText passwordEditText = new EditText(updateDB_Activity.this);
            passwordEditText.setHint("Password");
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            layout.addView(passwordEditText);

            final EditText nameEditText = new EditText(updateDB_Activity.this);
            nameEditText.setHint("Name");
            layout.addView(nameEditText);

            final EditText mobileNumEditText = new EditText(updateDB_Activity.this);
            mobileNumEditText.setHint("Mobile Number");
            mobileNumEditText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
            layout.addView(mobileNumEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Update user to the database
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String mobileNum = mobileNumEditText.getText().toString().trim();
                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && !mobileNum.isEmpty()) {
                    User newUser = new User(username,password,name,mobileNum);
                    ldbHelper.updateUser(db,newUser);
                    Toast.makeText(updateDB_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        updateBook.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Update Book");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText bookNameEditText = new EditText(updateDB_Activity.this);
            bookNameEditText.setHint("Book Name");
            layout.addView(bookNameEditText);

            final EditText authorEditText = new EditText(updateDB_Activity.this);
            authorEditText.setHint("Author Name");
            layout.addView(authorEditText);

            final EditText yearEditText = new EditText(updateDB_Activity.this);
            yearEditText.setHint("Year");
            layout.addView(yearEditText);

            final EditText synopsisEditText = new EditText(updateDB_Activity.this);
            synopsisEditText.setHint("Synopsis");
            layout.addView(synopsisEditText);

            final EditText availabilityEditText = new EditText(updateDB_Activity.this);
            availabilityEditText.setHint("Availability");
            layout.addView(availabilityEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Update user to the database
                String bookname = bookNameEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();
                int year = Integer.parseInt(yearEditText.getText().toString().trim());
                String synopsis = synopsisEditText.getText().toString().trim();
                int availability = Integer.parseInt(availabilityEditText.getText().toString().trim());
                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!bookname.isEmpty() && !author.isEmpty() && !String.valueOf(year).isEmpty() && !synopsis.isEmpty() &&  !String.valueOf(availability).isEmpty()) {
                    Book newBook = new Book(bookname,author,year,synopsis,availability);
                    ldbHelper.updateBook(db,newBook);
                    Toast.makeText(updateDB_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        addLendingRecord.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Update Lending Record");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText userRecordIDEditText = new EditText(updateDB_Activity.this);
            userRecordIDEditText.setHint("User Record ID");
            layout.addView(userRecordIDEditText);

            final EditText bookRecordIDEditText = new EditText(updateDB_Activity.this);
            bookRecordIDEditText.setHint("Book Record ID");
            layout.addView(bookRecordIDEditText);

            final EditText lendingDateEditText = new EditText(updateDB_Activity.this);
            lendingDateEditText.setHint("Lending Date (YYYY-MM-DD)");
            layout.addView(lendingDateEditText);

            final EditText expectedReturnDateEditText = new EditText(updateDB_Activity.this);
            expectedReturnDateEditText.setHint("Expected Return Date (YYYY-MM-DD)");
            layout.addView(expectedReturnDateEditText);

            final EditText returnDateEditText = new EditText(updateDB_Activity.this);
            returnDateEditText.setHint("Return Date (YYYY-MM-DD)");
            layout.addView(returnDateEditText);

            final EditText fineEditText = new EditText(updateDB_Activity.this);
            fineEditText.setHint("Fine");
            layout.addView(fineEditText);

            final EditText feePaidEditText = new EditText(updateDB_Activity.this);
            feePaidEditText.setHint("Fee Paid (1 for Yes, 0 for No)");
            layout.addView(feePaidEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Add lending record to the database
                String userRecordID = userRecordIDEditText.getText().toString().trim();
                String bookRecordID = bookRecordIDEditText.getText().toString().trim();
                String lendingDate = lendingDateEditText.getText().toString().trim();
                String expectedReturnDate = expectedReturnDateEditText.getText().toString().trim();
                String returnDate = returnDateEditText.getText().toString().trim();
                String fine = fineEditText.getText().toString().trim();
                String feePaid = feePaidEditText.getText().toString().trim();

                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!userRecordID.isEmpty() && !bookRecordID.isEmpty() && !lendingDate.isEmpty() && !expectedReturnDate.isEmpty() && !returnDate.isEmpty() && !fine.isEmpty() && !feePaid.isEmpty()) {
                    // Create lending record instance
                    LendingRecord newRecord = new LendingRecord(Integer.parseInt(userRecordID), Integer.parseInt(bookRecordID), lendingDate, expectedReturnDate, returnDate, Double.parseDouble(fine), Integer.parseInt(feePaid));
                    ldbHelper.updateLendingRecord(db, newRecord);
                    Toast.makeText(updateDB_Activity.this, "Lending Record Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        removeUser.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Remove User");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText userIDEditText = new EditText(updateDB_Activity.this);
            userIDEditText.setHint("User ID");
            layout.addView(userIDEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Remove", (dialog, which) -> {
                // Remove user record from the database
                String userID = userIDEditText.getText().toString().trim();

                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!userID.isEmpty()) {
                    ldbHelper.removeUser(db, Long.parseLong(userID));
                    Toast.makeText(updateDB_Activity.this, "User Removed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in User ID", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        removeBook.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Remove Book");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText bookIDEditText = new EditText(updateDB_Activity.this);
            bookIDEditText.setHint("Book ID");
            layout.addView(bookIDEditText);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Remove", (dialog, which) -> {
                // Remove book record from the database
                String bookID = bookIDEditText.getText().toString().trim();

                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!bookID.isEmpty()) {
                    ldbHelper.removeBook(db, Long.parseLong(bookID));
                    Toast.makeText(updateDB_Activity.this, "Book Removed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in Book ID", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        removeLendingRecord.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(updateDB_Activity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateDB_Activity.this);
            alertDialogBuilder.setTitle("Remove Lending Record");

            // Creating LinearLayout container for EditText fields
            LinearLayout layout = new LinearLayout(updateDB_Activity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Creating EditText fields
            final EditText recordIDEditText = new EditText(updateDB_Activity.this);
            recordIDEditText.setHint("Record ID");
            layout.addView(recordIDEditText);


            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("Add", (dialog, which) -> {
                // Add lending record to the database
                String recordID = recordIDEditText.getText().toString().trim();

                SQLiteDatabase db = ldbHelper.getWritableDatabase();
                if (!recordID.isEmpty()) {
                    ldbHelper.removeLendingRecord(db, Long.parseLong(recordID));
                    Toast.makeText(updateDB_Activity.this, "Lending Record removed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(updateDB_Activity.this, "Please fill in Record ID", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }
}