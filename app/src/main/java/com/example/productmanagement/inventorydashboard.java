package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class inventorydashboard extends AppCompatActivity {

    CardView adproduct, deleteproduct, viewproduct, viewinventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorydashboard);

        getSupportActionBar().setTitle("Inventory Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        adproduct = findViewById(R.id.addItems);
        deleteproduct = findViewById(R.id.deleteItems);
        viewproduct = findViewById(R.id.scanItems);
        viewinventory = findViewById(R.id.viewInventory);

        adproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inventorydashboard.this, addInventory.class));
            }
        });
        deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inventorydashboard.this, deleteInventory.class));
            }
        });
        viewinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inventorydashboard.this, view_edit.class));
            }
        });
        viewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inventorydashboard.this, search_inventory.class));
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