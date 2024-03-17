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
    ImageView addUser, addBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_db);
        addUser = findViewById(R.id.imageView1);
        addBook = findViewById(R.id.imageView4);

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
    }
}