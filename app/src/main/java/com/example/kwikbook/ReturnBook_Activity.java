package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReturnBook_Activity extends AppCompatActivity {
    private ListView lv;
    LibraryDatabaseHelper ldbHelper;
    int userID;
    String username;
    List<LendingRecord> lendingRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_return_book);
        lv = findViewById(R.id.list_books);

        ldbHelper = new LibraryDatabaseHelper(this);
        Intent receivingIntent = getIntent();
        username = receivingIntent.getStringExtra("USERNAME");
        SQLiteDatabase db = ldbHelper.getWritableDatabase();
        userID = ldbHelper.getUserID(db, username);

        displayLendingRecords();
    }

    private void displayLendingRecords() {
        lendingRecords = ldbHelper.getLendingRecordsWithEmptyReturnDate(userID);

        if (lendingRecords.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Lending Records")
                    .setMessage("No lending records yet! How about trying to lend some books?")
                    .setPositiveButton("Lend Book", (dialog, which) -> {
                        Intent lendBookIntent = new Intent(this, LendBook_Activity.class);
                        startActivity(lendBookIntent);
                    })
                    .setNegativeButton("Library Catalogue", (dialog, which) -> {
                        Intent libraryCatalogueIntent = new Intent(this, LibraryCatalogue_Activity.class);
                        startActivity(libraryCatalogueIntent);
                    })
                    .create()
                    .show();
        } else {
            ReturnBookAdapter adapter = new ReturnBookAdapter(lendingRecords);
            lv.setAdapter(adapter);
        }
    }

    private class ReturnBookAdapter extends BaseAdapter {
        private List<LendingRecord> lendingRecords;

        public ReturnBookAdapter(List<LendingRecord> lendingRecords) {
            this.lendingRecords = lendingRecords;
        }

        @Override
        public int getCount() {
            return lendingRecords.size();
        }

        @Override
        public Object getItem(int position) {
            return lendingRecords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_book, parent, false);
            }

            TextView bookNameTextView = convertView.findViewById(R.id.tvv_book_name);
            TextView lendingDateTextView = convertView.findViewById(R.id.tvv_lending_date);
            TextView returnDateTextView = convertView.findViewById(R.id.tvv_expected_return_date);
            TextView fineAmountTextView = convertView.findViewById(R.id.tvv_fine_amount);
            TextView feePaidStatus = convertView.findViewById(R.id.tvv_feePaidStatus);
            Button returnBookButton = convertView.findViewById(R.id.btn_return_book);

            SQLiteDatabase db = ldbHelper.getWritableDatabase();
            LendingRecord lendingRecord = lendingRecords.get(position);

            bookNameTextView.setText(ldbHelper.getBookName(db, (int) lendingRecord.getBookId()));
            lendingDateTextView.setText("Lending Date: " + lendingRecord.getLendingDate());
            returnDateTextView.setText("Expected Return Date: " + lendingRecord.getExpectedReturnDate());
            fineAmountTextView.setText("Fine Amount: " + lendingRecord.getFine());

            String feePaidStatuss;
            if (lendingRecord.getFee_paid() == 1){
                feePaidStatuss = "NOT PAID";
            } else{
                feePaidStatuss = "PAID";
            }

            feePaidStatus.setText("Fee Paid Status: " + feePaidStatuss);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date currentDate = new Date();
            String returnDate = dateFormat.format(currentDate);

            returnBookButton.setOnClickListener(v -> {
                if (lendingRecord.getFee_paid() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReturnBook_Activity.this);
                    builder.setTitle("Return Book Confirmation")
                            .setMessage("Are you sure you want to return this book?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                ldbHelper.returnBook(db, lendingRecord.getUserId(), lendingRecord.getBookId(), returnDate);
                                // Perform any necessary UI updates or actions after returning the book
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                } else{
                    Toast.makeText(ReturnBook_Activity.this, "Pay Fine First in order to return the book", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }
}
