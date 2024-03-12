package com.example.kwikbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHome_Activity extends AppCompatActivity {
    ImageView updateDB, lendingReports, penaltyInvoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        updateDB = findViewById(R.id.updateDBid);
        lendingReports = findViewById(R.id.lendingReportsid);
        penaltyInvoices = findViewById(R.id.penaltyInvoicesid);

        updateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iUpdateDB = new Intent(AdminHome_Activity.this, updateDB_Activity.class);
                startActivity(iUpdateDB);
            }
        });

        lendingReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLendingReports = new Intent(AdminHome_Activity.this, lendingReports_Activity.class);
                startActivity(iLendingReports);
            }
        });

        penaltyInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iPenaltyInvoices = new Intent(AdminHome_Activity.this, penaltyInvoices_Activity.class);
                startActivity(iPenaltyInvoices);
            }
        });

    }
}