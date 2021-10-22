package com.example.productmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView gotosell,gotoinventory;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Products Management Dashboard");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient));


        gotosell = findViewById(R.id.gotosell);
        gotoinventory = findViewById(R.id.gotoinventory);
        auth = FirebaseAuth.getInstance();

        gotosell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, selldashboard.class));
            }
        });

        gotoinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, inventorydashboard.class));
            }
        });
    }

    //Logout Menu work

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                auth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, user_Login.class));
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End logout menu work
}