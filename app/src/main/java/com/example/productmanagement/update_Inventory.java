package com.example.productmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class update_Inventory extends AppCompatActivity {

    TextView EEproductID,Eproductname,Eproductcategory,Eproductprice,Eproductquantity;
    EditText EEPname,EEPcategoory,EEPprice;
    Button EEButton;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        getSupportActionBar().setTitle("Edit Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        EEproductID = findViewById(R.id.EEproductID);
        Eproductname = findViewById(R.id.Eproductname);
        Eproductcategory = findViewById(R.id.Eproductcategory);
        Eproductprice = findViewById(R.id.Eproductprice);
        Eproductquantity=findViewById(R.id.Eproductquantity);

        EEPname = findViewById(R.id.EEPname);
        EEPcategoory = findViewById(R.id.EEPcategoory);
        EEPprice = findViewById(R.id.EEPprice);
        EEButton = findViewById(R.id.EEButton);

        EEButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        EEproductID.setText(getIntent().getStringExtra("ProductCode"));
        Eproductname.setText("Product Name: "+getIntent().getStringExtra("ProductName"));
        Eproductcategory.setText("Category: "+getIntent().getStringExtra("ProductCategory"));
        Eproductprice.setText("Price: " + getIntent().getStringExtra("ProductPrice") + " RM");
        String ShQuantity = getIntent().getExtras().get("productQuantity").toString();
        Eproductquantity.setText("Quantity: " + ShQuantity + " Pieces");
        String keyid = getIntent().getExtras().get("ProductCode").toString();

        EEPname.setText(getIntent().getStringExtra("ProductName"));
        EEPcategoory.setText(getIntent().getStringExtra("ProductCategory"));
        EEPprice.setText(getIntent().getStringExtra("ProductPrice"));




        firebaseAuth =FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String getuserEmail = finaluser.replace(".","");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(getuserEmail).child("Items").child(keyid);


    }

    public void update(){

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("productName").setValue(EEPname.getText().toString());
                dataSnapshot.getRef().child("productCategory").setValue(EEPcategoory.getText().toString());
                dataSnapshot.getRef().child("productPrice").setValue(EEPprice.getText().toString());

                Toast.makeText(update_Inventory.this,"Data Updated Successfully..!",Toast.LENGTH_LONG).show();

                update_Inventory.this.finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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