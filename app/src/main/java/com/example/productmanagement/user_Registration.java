package com.example.productmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class user_Registration extends AppCompatActivity {

    EditText name, email, password;
    Button register, gotologin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).hide();


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        gotologin = findViewById(R.id.gotologin);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> registerUser());

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_Registration.this, user_Login.class));
            }
        });
    }

    private void registerUser() {

        final String mname = name.getText().toString().trim();
        final String memail = email.getText().toString();
        String mpassword = password.getText().toString().trim();

        if (mname.isEmpty()) {
            name.setError("Name Required");
            name.requestFocus();
            return;
        }
        if (memail.isEmpty()) {
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        if (mpassword.isEmpty()) {
            password.setError("Password Required");
            password.requestFocus();
            return;
        }

        if (mpassword.length() < 6) {
            password.setError("Less length");
            password.requestFocus();
            return;
        }else{
            final SweetAlertDialog pDialog = new SweetAlertDialog(user_Registration.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#3498DB"));
            pDialog.setTitleText("Please Wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        mAuth.createUserWithEmailAndPassword(memail, mpassword)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        final User user = new User(
                                mname,
                                memail
                        );
                        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
                        assert usernameinfirebase != null;
                        String UserID = usernameinfirebase.getEmail();
                        assert UserID != null;
                        String resultemail = UserID.replace(".", "");

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(resultemail).child("UserDetails")
                                .setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {

                                Toast.makeText(user_Registration.this, "Registration Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(user_Registration.this, user_Login.class));
                            }

                        });
                        pDialog.dismiss();

                    } else {
                        Toast.makeText(user_Registration.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }


                });
        }

    }

}
