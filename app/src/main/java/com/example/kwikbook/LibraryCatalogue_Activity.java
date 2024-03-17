package com.example.kwikbook;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LibraryCatalogue_Activity extends AppCompatActivity {
    EditText et;
    ListView lv;
    ArrayAdapter<String> adapter;
    LibraryDatabaseHelper ldbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_catalogue);
        et = findViewById(R.id.editTextText2);
        lv = findViewById(R.id.listView);
        ldbHelper = new LibraryDatabaseHelper(this);
        db = ldbHelper.getReadableDatabase();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateSuggestions(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedBookName = (String) adapterView.getItemAtPosition(i);
            showBookDetails(selectedBookName);
        });
    }

    private void showBookDetails(String bookName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Book Details");
        Book book = ldbHelper.getBookDetailsByName(db, bookName);

        if (book != null) {
            String bookDetails = getBookDetails(book);
            builder.setMessage(Html.fromHtml(bookDetails));
        } else {
            builder.setMessage("Book details not found.");
        }

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            et.setText("");
            dialogInterface.dismiss();
        });

        builder.create().show();
    }

    @NonNull
    private static String getBookDetails(Book book) {
        String availability = book.getAvailability() == 0 ? "AVAILABLE TO LEND" : "NOT AVAILABLE TO LEND";

        return "<br>" + "<b>BookID:</b> " + book.getId() + "<br>" + "<br>" +
                "<b>Name:</b> " + book.getName() + "<br>" + "<br>" +
                "<b>Author:</b> " + book.getAuthor() + "<br>" + "<br>" +
                "<b>Year:</b> " + book.getYear() + "<br>" + "<br>" +
                "<b>Synopsis:</b> " + book.getSynopsis() + "<br>" + "<br>" +
                "<b>Availability:</b> " + "<i>"+availability+"</i>";
    }


    private void updateSuggestions(String string) {
        ArrayList<String> suggestions = new ArrayList<>();
        if (string.length() >= 2) {
            suggestions = ldbHelper.getBookSuggestions(db,string);
        }
        adapter.clear();
        adapter.addAll(suggestions);
        adapter.notifyDataSetChanged();
    }
}