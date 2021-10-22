package com.example.productmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class saleReportMonthly extends AppCompatActivity {

    EditText selctMonth, MYear;
    Button gentareDateBtn;
    TextView TotalSale,TotalQuantity;

    ListView lv;
    FirebaseListAdapter adapter;
    List<saleMonthlyAdapter> saleMonthlyAdapters;
    DatabaseReference totalsale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_report_monthly);

        getSupportActionBar().setTitle("View Monthly Sales Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));

        selctMonth = findViewById(R.id.selctMonth);
        MYear = findViewById(R.id.MYear);
        gentareDateBtn = findViewById(R.id.gentareDateBtn);
        TotalSale = findViewById(R.id.TotalSale);
        TotalQuantity =findViewById(R.id.TotalQuantity);

        saleMonthlyAdapters = new ArrayList<>();
        lv = findViewById(R.id.list_view);


        selctMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatepickup();
            }
        });
        gentareDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Vmonth = selctMonth.getText().toString();
                if (TextUtils.isEmpty(Vmonth)){
                    Toast.makeText(saleReportMonthly.this,"Please Select Month", Toast.LENGTH_SHORT).show();
                }else {
                getMonthlyData();}

            }
        });


    }

    public void getMonthlyData() {
        String getMonth = selctMonth.getText().toString();
        String getYear = MYear.getText().toString();

        Query query = FirebaseDatabase.getInstance().getReference("SaleDataMonthly")
                .child(getYear).child(getMonth);

        totalsale = FirebaseDatabase.getInstance().getReference("SaleDataMonthly")
                .child(getYear).child(getMonth);

        FirebaseListOptions<saleMonthlyAdapter> options = new FirebaseListOptions.Builder<saleMonthlyAdapter>()
                .setLayout(R.layout.view_sales_monthly)
                .setQuery(query, saleMonthlyAdapter.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView dateOfcreate = v.findViewById(R.id.dateOfcreate);
                TextView nameOfproduct = v.findViewById(R.id.nameOfproduct);
                TextView priceOfproduct = v.findViewById(R.id.priceOfproduct);
                TextView quantityOfproduct = v.findViewById(R.id.quantityOfproduct);


                saleMonthlyAdapter items1 = (saleMonthlyAdapter) model;
                dateOfcreate.setText("Creation Date: " + items1.getDateOfcreate().toString());
                nameOfproduct.setText("Product Name: " + items1.getNameOfproduct().toString());
                priceOfproduct.setText("Product Price: " + items1.getPriceOfproduct().toString());
                quantityOfproduct.setText("Product Quantity: " + items1.getQuantityOfproduct().toString());


                //total sale by month
                totalsale.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer totals = 0;
                        Integer quantity = 0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            saleMonthlyAdapter sma = ds.getValue(saleMonthlyAdapter.class);
                            Integer cost = Integer.valueOf(sma.getPriceOfproduct());
                            totals = totals + cost;

                            Integer quantitys = Integer.valueOf(sma.getQuantityOfproduct());
                            quantity = quantity + quantitys;

                            String totalss = Integer.toString(totals);
                            TotalSale.setText("Total Sale: " + totalss);
                            TotalQuantity.setText("Total Quantity: " + quantity);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };
        lv.setAdapter(adapter);
        adapter.startListening();

    }

    public void toDatepickup() {
        Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(saleReportMonthly.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) { // on date set
                        int mnth = selectedMonth + 1;
                        int yar = selectedYear;
                        String month = Integer.toString(mnth);
                        String year = Integer.toString(yar);

                        selctMonth.setText(month);
                        MYear.setText(year);

                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2030)
                .setTitle("Select Month & Year")
                .build().show();

    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}