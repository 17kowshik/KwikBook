package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class lendingReports_Activity extends AppCompatActivity {
    ListView lv;
    LibraryDatabaseHelper ldbHelper;
    List<LendingRecord> lendingRecords;
    int userID;
    EditText et;
    SQLiteDatabase db;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lending_reports);

        lv = findViewById(R.id.listView2);
        et = findViewById(R.id.editTextText3);
        bt = findViewById(R.id.button2);
        ldbHelper = new LibraryDatabaseHelper(this);
        db = ldbHelper.getWritableDatabase();
        bt.setOnClickListener(view -> {
            userID = Integer.parseInt(et.getText().toString());
            displayLendingRecords();
        });
    }

    private void displayLendingRecords() {
        if (userID == -1) {
            return;
        }

        lendingRecords = ldbHelper.getLendingRecords(userID);

        if (lendingRecords.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Lending Records")
                    .setMessage("No lending records yet for "+ldbHelper.getName(db, ldbHelper.getUsername(db, userID)))
                    .setPositiveButton("CANCEL", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        } else {
            LendingReportsAdapter adapter = new LendingReportsAdapter(lendingRecords);
            lv.setAdapter(adapter);
        }
    }

    private class LendingReportsAdapter extends BaseAdapter {
        private List<LendingRecord> lendingRecords;

        public LendingReportsAdapter(List<LendingRecord> lendingRecords) {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);
            }

            SQLiteDatabase db = ldbHelper.getWritableDatabase();
            TextView bookNameTextView = convertView.findViewById(R.id.history_text_book_name);
            TextView lendingDateTextView = convertView.findViewById(R.id.history_text_lending_date);
            TextView returnDateTextView = convertView.findViewById(R.id.history_text_return_date);
            TextView fineAmountTextView = convertView.findViewById(R.id.history_text_fine_amount);
            TextView fineStatusTextView = convertView.findViewById(R.id.history_text_fine_status);
            LendingRecord lendingRecord = lendingRecords.get(position);
            bookNameTextView.setText(ldbHelper.getBookName(db, (int) lendingRecord.getBookId()));
            lendingDateTextView.setText("Lending Date: " + lendingRecord.getLendingDate());
            returnDateTextView.setText("Return Date: " + lendingRecord.getReturnDate());
            fineAmountTextView.setText("Fine Amount: " + lendingRecord.getFine());
            String feePaidStatus;
            if (lendingRecord.getFee_paid() == 1){
                feePaidStatus = "NOT PAID";
            } else{
                feePaidStatus = "PAID";
            }
            fineStatusTextView.setText("Fine Status: " + feePaidStatus);
            return convertView;
        }
    }
}
