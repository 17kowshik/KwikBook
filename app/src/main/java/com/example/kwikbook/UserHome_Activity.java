package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserHome_Activity extends AppCompatActivity {
    TextView welcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        welcomeTextView = findViewById(R.id.userWelcomeID);

        Intent receivingIntent = getIntent();
        String username = receivingIntent.getStringExtra("USERNAME");

        LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(this);
        SQLiteDatabase db = ldbHelper.getReadableDatabase();
        String welcomeText = "Welcome "+ldbHelper.getName(db,username)+" !!!";
        welcomeTextView.setText(welcomeText);
    }
}