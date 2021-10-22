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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editSellesRecord extends AppCompatActivity {

    EditText SEPname,SEPcategoory,SEPprice,SEPquantity,SEPcode;
    Button SellUpdate;
    TextView SEproductID;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_selles_record);

        getSupportActionBar().setTitle("Edit Sale's Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        SEproductID = findViewById(R.id.SEproductID);
        SEPname = findViewById(R.id.SEPname);
        SEPcategoory = findViewById(R.id.SEPcategoory);
        SEPprice = findViewById(R.id.SEPprice);
        SEPquantity = findViewById(R.id.SEPquantity);
        SEPcode = findViewById(R.id.SEPcode);
        SellUpdate = findViewById(R.id.SellUpdate);


        SellUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sellupdate();
            }
        });

        SEproductID.setText(getIntent().getStringExtra("ID"));
        String keyid = getIntent().getExtras().get("ID").toString();

        SEPname.setText(getIntent().getStringExtra("ProductName"));
        SEPcategoory.setText(getIntent().getStringExtra("ProductCategory"));
        SEPprice.setText(getIntent().getStringExtra("ProductPrice"));
        SEPquantity.setText(getIntent().getStringExtra("productQuantity"));
        SEPcode.setText(getIntent().getStringExtra("ProductCode"));


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("sales").child(keyid);
    }
        public void Sellupdate(){

            mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("sproductName").setValue(SEPname.getText().toString());
                    dataSnapshot.getRef().child("sproductCategory").setValue(SEPcategoory.getText().toString());
                    dataSnapshot.getRef().child("sproductPrice").setValue(SEPprice.getText().toString());
                    dataSnapshot.getRef().child("sproductQuantity").setValue(SEPquantity.getText().toString());
                    dataSnapshot.getRef().child("sproductCode").setValue(SEPcode.getText().toString());

                    Toast.makeText(editSellesRecord.this,"Data Updated Successfully..!",Toast.LENGTH_LONG).show();

                    editSellesRecord.this.finish();

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