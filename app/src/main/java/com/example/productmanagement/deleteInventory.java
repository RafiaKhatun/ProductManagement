package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;

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

public class deleteInventory extends AppCompatActivity {

    EditText DproductCode;
    Button Dproduct;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_inventory);

        getSupportActionBar().setTitle("Delete Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        DproductCode = findViewById(R.id.DproductCode);
        Dproduct = findViewById(R.id.Dproduct);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        Dproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletefrmdatabase();
            }
        });


    }
    public void deletefrmdatabase()
    {
        String deleteproduct = DproductCode.getText().toString();
        if (TextUtils.isEmpty(deleteproduct)) {
            DproductCode.setError("Please Enter Product Code");
            DproductCode.requestFocus();
            return;
        }


        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(deleteproduct)){
            databaseReference.child(resultemail).child("Items").child(deleteproduct).removeValue();
            Toast.makeText(deleteInventory.this,"Item has Deleted",Toast.LENGTH_SHORT).show();
            DproductCode.setText("");
        }
        else{
            Toast.makeText(deleteInventory.this,"Please Re-try Again",Toast.LENGTH_SHORT).show();
        }
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}