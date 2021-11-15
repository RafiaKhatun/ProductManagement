package com.example.productmanagement;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class addsell extends AppCompatActivity {

    TextView SellingProName, SellingProCategory, SellingProPrice, TotalPrice, availableQuantity;
    EditText SellingProQuantity;
    Spinner itemlist;
    Button SubmitSell;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference selldatabase, updatequantitydatabase, dataformonthlysale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsell);

        getSupportActionBar().setTitle("Add Sale");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));


        itemlist = findViewById(R.id.itemlist);
        SellingProName = findViewById(R.id.SellingProName);
        SellingProCategory = findViewById(R.id.SellingProCategory);
        SellingProPrice = findViewById(R.id.SellingProPrice);
        SellingProQuantity = findViewById(R.id.SellingProQuantity);
        TotalPrice = findViewById(R.id.TotalPrice);
        availableQuantity = findViewById(R.id.availableQuantity);


        //Sale button work Start
        selldatabase = FirebaseDatabase.getInstance().getReference("sales");
        SubmitSell = findViewById(R.id.SubmitSell);
        SubmitSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String avail = availableQuantity.getText().toString();
                int availe = Integer.parseInt(avail);

                if (availe <= 1) {
                    Toast.makeText(addsell.this, "Stock Out! Please Add in inventory first", Toast.LENGTH_SHORT).show();
                } else {
                    Updateinvetory();
                    Sale();
                    dataformonthly();
                }

            }
        });

//Sell button work end

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String getuserEmail = finaluser.replace(".", "");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(getuserEmail).child("Items");

        //Start to get value in spinner from database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("productCode").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner areaSpinner = (Spinner) findViewById(R.id.itemlist);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(addsell.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //End to get value in spinner from database

        //Start getiing data as selected item
        itemlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                itemlist.setSelection(i);
                final String PID = itemlist.getSelectedItem().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                        .child(getuserEmail).child("Items");

                databaseReference.child(PID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Items items = snapshot.getValue(Items.class);
                        SellingProName.setText(items.getProductName());
                        SellingProCategory.setText(items.getProductCategory());
                        SellingProPrice.setText(items.getProductPrice());
                        availableQuantity.setText(items.getProductQuantity());

                        textcolor();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//End getting data as selected items
//Start Calculation of product

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!SellingProPrice.getText().toString().equals("") &&
                        !SellingProQuantity.getText().toString().equals("")) {

                    int a = Integer.parseInt(SellingProPrice.getText().toString());
                    int b = Integer.parseInt(SellingProQuantity.getText().toString());
                    int result = a * b;
                    ;
                    String Value = String.valueOf(result);

                    TotalPrice.setText(Value);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        SellingProPrice.addTextChangedListener(textWatcher);
        SellingProQuantity.addTextChangedListener(textWatcher);
        //End Calculation of product

    }

    //Start submition
    public void Sale() {
        try {
            String SproductCode = itemlist.getSelectedItem().toString();
            String SproductName = SellingProName.getText().toString();
            String SproductCategory = SellingProCategory.getText().toString();
            String SproductPrice = TotalPrice.getText().toString();
            String SproductQuantity = SellingProQuantity.getText().toString();

            if (TextUtils.isEmpty(SproductQuantity)) {
                SellingProQuantity.setError("Please Enter Quantity");
                SellingProQuantity.requestFocus();
                return;
            }
            //Create unique id every purs
            String Sid = selldatabase.push().getKey();

            final FirebaseUser users = firebaseAuth.getCurrentUser();
            String finaluser = users.getEmail();

            sellAdapter sellAdapter = new sellAdapter(SproductCode, SproductName, SproductCategory, SproductPrice,
                    SproductQuantity, Sid, finaluser);
            selldatabase.child(Sid).setValue(sellAdapter);
        } catch (Exception error1) {
            Log.e(TAG, "The exception caught while executing the process. (Error1)");
            error1.printStackTrace();
        }
    }

    //update quantity
    public void Updateinvetory() {
        String k = SellingProQuantity.getText().toString();
        String SproductCode = itemlist.getSelectedItem().toString();

        if (k.isEmpty()) {
            SellingProQuantity.setError("Its Requird");
            SellingProQuantity.requestFocus();
            return;
        } else {
            int a = Integer.parseInt(availableQuantity.getText().toString());
            int b = Integer.parseInt(SellingProQuantity.getText().toString());
            int result = a - b;
            ;
            String Value = String.valueOf(result);


            firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            String finaluser = user.getEmail();
            String getuserEmail = finaluser.replace(".", "");

            updatequantitydatabase = FirebaseDatabase.getInstance().getReference("Users").child(getuserEmail).child("Items").child(SproductCode);
            updatequantitydatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("productQuantity").setValue(Value);
                    addsell.this.finish();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
    //Submit button insider work end

    //start Inserting new table for monthly sales report
    public void dataformonthly(){
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String days = Integer.toString(day);
        int month = calendar.get(Calendar.MONTH);
        String months = Integer.toString(month+1);
        int year = calendar.get(Calendar.YEAR);
        String years = Integer.toString(year);

        String date = days + "/" + months + "/" + years;

        String productName = SellingProName.getText().toString();
        String totalPrice = TotalPrice.getText().toString();
        String productQuantity = SellingProQuantity.getText().toString();

        if (TextUtils.isEmpty(productQuantity)) {
            SellingProQuantity.setError("Please Enter Quantity");
            SellingProQuantity.requestFocus();
            return;
        }else {
            dataformonthlysale = FirebaseDatabase.getInstance().getReference("SaleDataMonthly")
            .child(years).child(months);
            //Create unique id every purs
            String Sid = dataformonthlysale.push().getKey();

            saleMonthlyAdapter saleMonthlyAdapter = new saleMonthlyAdapter(date, productName, totalPrice, productQuantity);
            dataformonthlysale.child(Sid).setValue(saleMonthlyAdapter);
        }

    }
    //end Inserting new table for monthly sales report
    public void textcolor() {
        String availabletext = availableQuantity.getText().toString();
        int number = Integer.parseInt(availabletext);

        if (number <= 10) {
            availableQuantity.setTextColor(Color.RED);
            new SweetAlertDialog(this)
                    .setTitleText("Low Stock!!")
                    .show();
        }
        if (number >= 11) {
            availableQuantity.setTextColor(Color.GREEN);
        }
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}