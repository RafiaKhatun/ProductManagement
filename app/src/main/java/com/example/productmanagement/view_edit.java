package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;
import java.util.List;

public class view_edit extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    List<Items> items;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        getSupportActionBar().setTitle("View Inventory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        items = new ArrayList<>();
        lv = findViewById(R.id.list_view);

        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String getuserEmail = finaluser.replace(".","");


        Query query = FirebaseDatabase.getInstance().getReference("Users").child(getuserEmail).child("Items");

        FirebaseListOptions<Items> options = new FirebaseListOptions.Builder<Items>()
                .setLayout(R.layout.view_inventory)
                .setQuery(query,Items.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView Vproductcode = v.findViewById(R.id.VproductCode);
                TextView Vproductname = v.findViewById(R.id.VproductName);
                TextView Vproductcategory = v.findViewById(R.id.VproductCategory);
                TextView Vproductprice = v.findViewById(R.id.VproductPrice);
                TextView Vproductquantity = v.findViewById(R.id.VproductQuantity);

                Items items1 = (Items) model;
                Vproductname.setText("Product Name: " + items1.getProductName().toString());
                Vproductcategory.setText("Product Category: " + items1.getProductCategory().toString());
                Vproductprice.setText("Product Price: " + items1.getProductPrice() + " RM".toString());
                Vproductcode.setText("Product Code: " + items1.getProductCode().toString());
                Vproductquantity.setText("Quantity: " + items1.getProductQuantity() + " pieces".toString());

            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent updatedelete = new Intent(view_edit.this, update_Inventory.class);
                Items T = (Items) adapterView.getItemAtPosition(i);
                updatedelete.putExtra("ProductName", T.getProductName());
                updatedelete.putExtra("ProductCategory", T.getProductCategory());
                updatedelete.putExtra("ProductPrice", T.getProductPrice());
                updatedelete.putExtra("productQuantity",T.getProductQuantity());
                updatedelete.putExtra("ProductCode",T.getProductCode());

                startActivity(updatedelete);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //back button
    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}