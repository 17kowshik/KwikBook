package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserHome_Activity extends AppCompatActivity {
    TextView welcomeTextView;
    ImageView libraryCatalogue, lendBook, returnBook, pay, lendingHistory, contactUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        welcomeTextView = findViewById(R.id.userWelcomeID);
        libraryCatalogue = findViewById(R.id.libraryCatalogue_iv);
        lendBook = findViewById(R.id.lendBook_iv);
        returnBook = findViewById(R.id.returnBook_iv);
        pay = findViewById(R.id.pay_iv);
        lendingHistory = findViewById(R.id.lendingHistory_iv);
        contactUs = findViewById(R.id.contactUs_iv);

        libraryCatalogue.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, LibraryCatalogue_Activity.class);
            startActivity(i);
        });

        lendBook.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, LendBook_Activity.class);
            startActivity(i);
        });

        returnBook.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, ReturnBook_Activity.class);
            startActivity(i);
        });

        pay.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, Pay_Activity.class);
            startActivity(i);
        });

        lendingHistory.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, LendingHistory_Activity.class);
            startActivity(i);
        });

        contactUs.setOnClickListener(view -> {
            Intent i = new Intent(UserHome_Activity.this, ContactUs_Activity.class);
            startActivity(i);
        });

        Intent receivingIntent = getIntent();
        String username = receivingIntent.getStringExtra("USERNAME");

        LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(this);
        SQLiteDatabase db = ldbHelper.getReadableDatabase();
        String welcomeText = "Welcome "+ldbHelper.getName(db,username)+" !!!";
        welcomeTextView.setText(welcomeText);
    }
}