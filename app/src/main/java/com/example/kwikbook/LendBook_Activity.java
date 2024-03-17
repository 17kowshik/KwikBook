package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LendBook_Activity extends AppCompatActivity {
    EditText e;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lend_book);

        e=findViewById(R.id.editTextText);
        b=findViewById(R.id.button);

        Intent receivingIntent = getIntent();
        String username = receivingIntent.getStringExtra("USERNAME");

        b.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper=new LibraryDatabaseHelper(this);
            SQLiteDatabase db= ldbHelper.getWritableDatabase();
            int userID=ldbHelper.getUsserID(db, username);
        });

    }
}