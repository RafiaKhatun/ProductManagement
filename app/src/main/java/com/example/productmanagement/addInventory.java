package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addInventory extends AppCompatActivity {

    private EditText productName, productCategory, productPrice, productCode,productQuantity;
    private FirebaseAuth firebaseAuth;
    Button addinventory;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        getSupportActionBar().setTitle("Add Inventory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        firebaseAuth = FirebaseAuth.getInstance();
        productName = findViewById(R.id.productName);
        productCategory = findViewById(R.id.productCategory);
        productPrice = findViewById(R.id.productPrice);
        productCode = findViewById(R.id.productCode);
        productQuantity = findViewById(R.id.productQuantity);
        addinventory = findViewById(R.id.addinventory);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");

        addinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addinventory();
            }
        });
    }

    // addding inventory to databse
    public void addinventory() {
        String productname = productName.getText().toString();
        String productcategory = productCategory.getText().toString();
        String productprice = productPrice.getText().toString();
        String productquantity = productQuantity.getText().toString();
        String productcode = productCode.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");

        if (productcode.isEmpty()) {
            productCode.setError("It's Empty");
            productCode.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(productname) && !TextUtils.isEmpty(productcategory) && !TextUtils.isEmpty(productprice)) {

            Items items = new Items(productname, productcategory, productprice,productquantity, productcode);
            databaseReference.child(resultemail).child("Items").child(productcode).setValue(items);
            databaseReferencecat.child(resultemail).child("ItemByCategory").child(productcategory).child(productcode).setValue(items);
            productName.setText("");
            productCode.setText("");
            productPrice.setText("");
            Toast.makeText(addInventory.this, productname + " Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(addInventory.this, inventorydashboard.class));
        } else {
            Toast.makeText(addInventory.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}