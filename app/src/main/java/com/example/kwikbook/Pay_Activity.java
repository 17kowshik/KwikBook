package com.example.kwikbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class Pay_Activity extends AppCompatActivity {
    private ListView listView;
    LibraryDatabaseHelper ldbHelper;
    int userID;
    String username;
    List<LendingRecord> lendingRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // Initialize database helper and SharedPreferences
        ldbHelper = new LibraryDatabaseHelper(this);
        Intent receivingIntent = getIntent();
        username = receivingIntent.getStringExtra("USERNAME");
        SQLiteDatabase db = ldbHelper.getWritableDatabase();
        userID = ldbHelper.getUserID(db, username);

        listView = findViewById(R.id.list_fines);
        displayLendingRecordsWithFines();
    }

    private void displayLendingRecordsWithFines() {
        lendingRecords = ldbHelper.getLendingRecordsWithFinesGreaterThanZero(userID);

        if (lendingRecords.isEmpty()) {
            Toast.makeText(this, "No fines to display", Toast.LENGTH_SHORT).show();
        } else {
            LendingRecordListAdapter adapter = new LendingRecordListAdapter(lendingRecords);
            listView.setAdapter(adapter);
        }
    }

    private class LendingRecordListAdapter extends BaseAdapter {
        private List<LendingRecord> lendingRecords;

        public LendingRecordListAdapter(List<LendingRecord> lendingRecords) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_fine, parent, false);
            }

            SQLiteDatabase db = ldbHelper.getWritableDatabase();
            TextView bookNameTextView = convertView.findViewById(R.id.text_book_name);
            TextView lendingDateTextView = convertView.findViewById(R.id.text_lending_date);
            TextView returnDateTextView = convertView.findViewById(R.id.text_return_date);
            TextView fineAmountTextView = convertView.findViewById(R.id.text_fine_amount);
            Button payFineButton = convertView.findViewById(R.id.btn_pay_fine);

            LendingRecord lendingRecord = lendingRecords.get(position);
            bookNameTextView.setText(ldbHelper.getBookName(db, (int) lendingRecord.getBookId()));
            lendingDateTextView.setText("Lending Date: " + lendingRecord.getLendingDate());
            returnDateTextView.setText("Return Date: " + lendingRecord.getReturnDate());
            fineAmountTextView.setText("Fine Amount: " + lendingRecord.getFine());
            int isFinePaid = lendingRecord.getFee_paid();
            if (isFinePaid == 0) {
                payFineButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                payFineButton.setText("PAID");
                payFineButton.setEnabled(false);
            } else {
                payFineButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                payFineButton.setText("Pay Fine");
                payFineButton.setEnabled(true);
            }

            payFineButton.setOnClickListener(v -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Date currentDate = new Date();

                String payingDate = dateFormat.format(currentDate);
                String payingTime = timeFormat.format(currentDate);

                // Create the payment details in HTML format
                String paymentDetails = "<b>User Name:</b> " +
                        ldbHelper.getName(db, username) +
                        "<br>" +
                        "<b>Book Name:</b> " +
                        ldbHelper.getBookName(db, (int) lendingRecord.getBookId()) +
                        "<br>" +
                        "<b>Lending Date:</b> " +
                        lendingRecord.getLendingDate() +
                        "<br>" +
                        "<b>Fine Paid:</b> " +
                        lendingRecord.getFine() +
                        "<br>" +
                        "<b>Paying Date:</b>" +
                        payingDate +
                        "<br>" +
                        "<b>Paying Time:</b>" +
                        payingTime;

                AlertDialog.Builder builder = new AlertDialog.Builder(Pay_Activity.this);
                builder.setTitle("BILL")
                        .setMessage(Html.fromHtml(paymentDetails, Html.FROM_HTML_MODE_COMPACT))
                        .setPositiveButton("Paid", (dialog, which) -> {
                            ldbHelper.feePaidUpdate(Pay_Activity.this, db,lendingRecord.getBookId(), lendingRecord.getUserId());

                            payFineButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                            payFineButton.setText("PAID");
                            payFineButton.setEnabled(false);

                        })
                        .show();
            });

            return convertView;
        }
    }
}
