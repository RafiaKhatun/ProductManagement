package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class selldashboard extends AppCompatActivity {

    CardView sellProduct,displayrecord,monthlyreportpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selldashboard);

        getSupportActionBar().setTitle("Sale Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        displayrecord = findViewById(R.id.displayrecord);
        displayrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selldashboard.this, displaySellsRecord.class));
            }
        });
        sellProduct = findViewById(R.id.sellProduct);
        sellProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selldashboard.this, addsell.class));
            }
        });
        monthlyreportpage = findViewById(R.id.monthlyreportpage);
        monthlyreportpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selldashboard.this, saleReportMonthly.class));
            }
        });


    }
    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}