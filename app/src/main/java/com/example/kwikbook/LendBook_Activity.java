package com.example.kwikbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LendBook_Activity extends AppCompatActivity {
    EditText e;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lend_book);

        e = findViewById(R.id.editTextText);
        b = findViewById(R.id.button);

        Intent receivingIntent = getIntent();
        String username = receivingIntent.getStringExtra("USERNAME");

        b.setOnClickListener(view -> {
            LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(this);
            SQLiteDatabase db = ldbHelper.getWritableDatabase();
            int userID = ldbHelper.getUserID(db, username);
            int bookID = Integer.parseInt(e.getText().toString());

            int availability = ldbHelper.getAvailability(db, bookID);

            if (availability == 0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LendBook_Activity.this);
                alertDialogBuilder.setTitle("Confirm Book Lending");

                Date today = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(today);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 30);
                SimpleDateFormat dateFormat30 = new SimpleDateFormat("dd-MM-yyyy");
                String thirtyDaysFromNow = dateFormat30.format(calendar.getTime());

                String lendingDetails = "<br>" +
                        "<b>The Book you want to lend is :</b> " + ldbHelper.getBookName(db, bookID) + "<br>" +
                        "<br>" +
                        "<b>Lending Date :</b> " + "<i>" + formattedDate + "</i>" + "<br>" +
                        "<br>" +
                        "<b>Deadline to Return :</b> " + "<i>" + thirtyDaysFromNow + "</i>" + "<br>" +
                        "<br>" +
                        "<font color='red'><b><i>Note:</i></b> After the deadline, a 1 rupee fine is imposed for every day late.</font><br>" +
                        "<br>";
                alertDialogBuilder.setMessage(Html.fromHtml(lendingDetails));

                alertDialogBuilder.setPositiveButton("CONFIRM LENDING", (dialogInterface, i) -> {
                    ldbHelper.lendBook(db, LendBook_Activity.this, userID, bookID, formattedDate, thirtyDaysFromNow);
                    Toast.makeText(this, "Lent Successfully !!!", Toast.LENGTH_SHORT).show();
                });

                alertDialogBuilder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                Toast.makeText(this, "Sorry, Book isn't Available to lend 😢", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LibraryDatabaseHelper dbHelper = new LibraryDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }
}
