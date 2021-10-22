package com.example.productmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class search_inventory extends AppCompatActivity {

    EditText SproductCode;
    Button SButton;
    TextView SSproductcode,SSproductname,SSproductcategory,SSproductprice,SSproductquantity;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_inventory);

        getSupportActionBar().setTitle("Search Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        SSproductcode = findViewById(R.id.SSproductcode);
        SSproductname = findViewById(R.id.SSproductname);
        SSproductcategory = findViewById(R.id.SSproductcategory);
        SSproductprice = findViewById(R.id.SSproductprice);
        SSproductquantity= findViewById(R.id.SSproductquantity);

        SButton = findViewById(R.id.SButton);
        SproductCode = findViewById(R.id.SproductCode);

        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String getuserEmail = finaluser.replace(".","");




        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(getuserEmail).child("Items");



        SButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search();

            }
        });

    }

    public void search(){

        String key = SproductCode.getText().toString();
        if(!TextUtils.isEmpty(key)) {
            databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Items items = snapshot.getValue(Items.class);

                    SSproductcode.setText("Code: " + items.getProductCode());
                    SSproductname.setText("Product Name: " + items.getProductName());
                    SSproductcategory.setText("Category: " + items.getProductCategory());
                    SSproductprice.setText("Price: " + items.getProductPrice());
                    SSproductquantity.setText("Quantity: " + items.getProductQuantity());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Toast.makeText(search_inventory.this,"Please Enter Product Code",Toast.LENGTH_SHORT).show();
        }

    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}