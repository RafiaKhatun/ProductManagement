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

public class displaySellsRecord extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    List<sellAdapter> items;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sells_record);

        getSupportActionBar().setTitle("Sales Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        items = new ArrayList<>();
        lv = findViewById(R.id.list_view);

        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String getuserEmail = finaluser.replace(".","");


        Query query = FirebaseDatabase.getInstance().getReference("sales");

        FirebaseListOptions<sellAdapter> options = new FirebaseListOptions.Builder<sellAdapter>()
                .setLayout(R.layout.view_selles)
                .setQuery(query,sellAdapter.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView SellID = v.findViewById(R.id.SellID);
                TextView SellProductName = v.findViewById(R.id.SellProductName);
                TextView SellProductCategory = v.findViewById(R.id.SellProductCategory);
                TextView SellProductPrice = v.findViewById(R.id.SellProductPrice);
                TextView SellProductQuantity = v.findViewById(R.id.SellProductQuantity);
                TextView SellerEmail = v.findViewById(R.id.SellerEmail);
                TextView SellproductCODE = v.findViewById(R.id.SellproductCODE);


                sellAdapter items1 = (sellAdapter) model;
                SellID.setText(items1.getSsellId().toString());
                SellProductName.setText("Product Name: " + items1.getSproductName().toString());
                SellProductCategory.setText("Product Category: " + items1.getSproductCategory().toString());
                SellProductPrice.setText("Product Price: " + items1.getSproductPrice().toString());
                SellProductQuantity.setText("Product Quantity: " + items1.getSproductQuantity().toString());
                SellerEmail.setText("Sale by: " + items1.getSadminEmail().toString());
                SellproductCODE.setText("Product Code: " + items1.getSproductCode().toString());

            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent update = new Intent(displaySellsRecord.this, editSellesRecord.class);
                sellAdapter T = (sellAdapter) adapterView.getItemAtPosition(i);

                update.putExtra("ID",T.getSsellId());
                update.putExtra("ProductName", T.getSproductName());
                update.putExtra("ProductCategory", T.getSproductCategory());
                update.putExtra("ProductPrice", T.getSproductPrice());
                update.putExtra("productQuantity",T.getSproductQuantity());
                update.putExtra("ProductCode",T.getSproductCode());

                startActivity(update);
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
    @Override
    public boolean onSupportNavigateUp() {
        Intent i =new Intent(displaySellsRecord.this, selldashboard.class);
        startActivity(i);
        return true;
    }
}