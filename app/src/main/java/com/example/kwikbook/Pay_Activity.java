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
    Map<Integer, Boolean> paidStatusMap; // Map to track paid status

    // SharedPreferences and Editor for saving and retrieving paid status
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // Initialize database helper and SharedPreferences
        ldbHelper = new LibraryDatabaseHelper(this);
        sharedPreferences = getSharedPreferences("PaidStatusPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent receivingIntent = getIntent();
        username = receivingIntent.getStringExtra("USERNAME");
        SQLiteDatabase db = ldbHelper.getWritableDatabase();
        userID = ldbHelper.getUserID(db, username);

        listView = findViewById(R.id.list_fines);
        displayLendingRecordsWithFines();

        // Initialize the paid status map
        paidStatusMap = new HashMap<>();
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

            // Retrieve the paid status from SharedPreferences
            boolean isFinePaid = sharedPreferences.getBoolean("isFinePaid_" + position, false);

            // Update button text and color based on the paid status
            if (isFinePaid) {
                payFineButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                payFineButton.setText("PAID");
                payFineButton.setEnabled(false);
            } else {
                payFineButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                payFineButton.setText("Pay Fine");
                payFineButton.setEnabled(true);
            }

            // Set the click listener for the "Pay Fine" button
            payFineButton.setOnClickListener(v -> {
                // Get current date and time
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Date currentDate = new Date();

                String payingDate = dateFormat.format(currentDate);
                String payingTime = timeFormat.format(currentDate);

                // Create the payment details in HTML format
                StringBuilder paymentDetails = new StringBuilder();
                paymentDetails.append("<b>User Name:</b> ")
                        .append(ldbHelper.getName(db, username))
                        .append("<br>")
                        .append("<b>Book Name:</b> ")
                        .append(ldbHelper.getBookName(db, (int) lendingRecord.getBookId()))
                        .append("<br>")
                        .append("<b>Lending Date:</b> ")
                        .append(lendingRecord.getLendingDate())
                        .append("<br>")
                        .append("<b>Return Date:</b> ")
                        .append(lendingRecord.getReturnDate())
                        .append("<br>")
                        .append("<b>Fine Paid:</b> ")
                        .append(lendingRecord.getFine())
                        .append("<br>")
                        .append("<b>Paying Date:</b>")
                        .append(payingDate)
                        .append("<br>")
                        .append("<b>Paying Time:</b>")
                        .append(payingTime);

                // Show an alert dialog with the payment details
                AlertDialog.Builder builder = new AlertDialog.Builder(Pay_Activity.this);
                builder.setTitle("BILL")
                        .setMessage(Html.fromHtml(paymentDetails.toString(), Html.FROM_HTML_MODE_COMPACT))
                        .setPositiveButton("Paid", (dialog, which) -> {
                            // Update the paid status map
                            paidStatusMap.put(position, true);

                            // Save the paid status to SharedPreferences
                            editor.putBoolean("isFinePaid_" + position, true);
                            editor.apply();

                            // Update the button state
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
