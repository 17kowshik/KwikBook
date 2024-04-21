package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class penaltyInvoices_Activity extends AppCompatActivity {
    ListView lv;
    LibraryDatabaseHelper ldbHelper;
    List<LendingRecord> lendingRecords;
    int userID;
    String username;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_penalty_invoices);
        lv = findViewById(R.id.PenaltyInvoicesListView);
        ldbHelper = new LibraryDatabaseHelper(this);
        db = ldbHelper.getWritableDatabase();
        displayLendingRecords();
    }

    private void displayLendingRecords() {
        lendingRecords = ldbHelper.getAllLendingRecords(db);

        if (lendingRecords.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Lending Records")
                    .setMessage("Yayy!!, No one with Pending Fines !!")
                    .setPositiveButton("BACK", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        } else {
            PenaltyAdapter adapter = new PenaltyAdapter(lendingRecords);
            lv.setAdapter(adapter);
        }
    }

    private class PenaltyAdapter extends BaseAdapter {
        private List<LendingRecord> lendingRecords;

        public PenaltyAdapter(List<LendingRecord> lendingRecords) {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_generate_fines, parent, false);
            }

            SQLiteDatabase db = ldbHelper.getWritableDatabase();
            TextView userNameTextView = convertView.findViewById(R.id.textViewUserName);
            TextView bookNameTextView = convertView.findViewById(R.id.textViewBookName);
            TextView lendingDateTextView = convertView.findViewById(R.id.textViewLendingDate);
            TextView expectedReturnDateTextView = convertView.findViewById(R.id.textViewExpectedReturnDate);
            TextView fineAmountTextView = convertView.findViewById(R.id.textViewFine);

            LendingRecord lendingRecord = lendingRecords.get(position);
            userID = (int) lendingRecord.getUserId();
            username = ldbHelper.getUsername(db, userID);
            String name = ldbHelper.getName(db,username);
            userNameTextView.setText("User: " + userID+" - "+name);
            bookNameTextView.setText("Book: " + ldbHelper.getBookName(db, (int) lendingRecord.getBookId()));
            lendingDateTextView.setText("Lending Date: " + lendingRecord.getLendingDate());
            expectedReturnDateTextView.setText("Expected Return Date: " + lendingRecord.getExpectedReturnDate());
            fineAmountTextView.setText("Fine Amount: " + lendingRecord.getFine());

            return convertView;

        }
    }

}