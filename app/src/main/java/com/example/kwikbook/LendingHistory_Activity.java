package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class LendingHistory_Activity extends AppCompatActivity {
    ListView lv;
    LibraryDatabaseHelper ldbHelper;
    List<LendingRecord> lendingRecords;
    int userID;
    TextView tv;
    String username;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lending_history);
        lv = findViewById(R.id.LendingHistoryListview);
        tv = findViewById(R.id.textView7);
        ldbHelper = new LibraryDatabaseHelper(this);
        Intent receivingIntent = getIntent();
        username = receivingIntent.getStringExtra("USERNAME");
        db = ldbHelper.getWritableDatabase();
        userID = ldbHelper.getUserID(db, username);
        displayLendingRecords();
    }

    private void displayLendingRecords() {
        lendingRecords = ldbHelper.getLendingRecords(userID);

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
            String name = ldbHelper.getName(db, username);
            tv.setText(name+"'s Lending History");
            LendingHistoryAdapter adapter = new LendingHistoryAdapter(lendingRecords);
            lv.setAdapter(adapter);
        }
    }
    private class LendingHistoryAdapter extends BaseAdapter {
        private List<LendingRecord> lendingRecords;

        public LendingHistoryAdapter(List<LendingRecord> lendingRecords) {
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