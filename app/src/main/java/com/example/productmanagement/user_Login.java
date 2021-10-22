package com.example.productmanagement;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class user_Login extends AppCompatActivity {

    private ImageButton btRegister;
    private Button login;
    EditText Lemail,Lpassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btRegister  = findViewById(R.id.btRegister);
        login     = findViewById(R.id.login);
        Lemail =findViewById(R.id.Lemail);
        Lpassword = findViewById(R.id.Lpassword);
        auth = FirebaseAuth.getInstance();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(user_Login.this, user_Registration.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Lemail.getText().toString().trim();
                String password = Lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Lemail.setError("Email Required");
                    Lemail.requestFocus();
                    return;
                }if (TextUtils.isEmpty(password)){
                    Lpassword.setError("Password Required");
                    Lpassword.requestFocus();
                    return;
                }

                if (password.length() < 6){
                    Lpassword.setError("Password should be at least 6 characters long");
                    Lpassword.requestFocus();
                    return;
                }else{
                    loginUser();
                }


            }
        });
    }
    public void loginUser(){
        final String memail = Lemail.getText().toString();
        String mpassword = Lpassword.getText().toString().trim();

        final SweetAlertDialog pDialog = new SweetAlertDialog(user_Login.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3498DB"));
        pDialog.setTitleText("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        auth.signInWithEmailAndPassword(memail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(user_Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(user_Login.this, MainActivity.class));
                }
                else{
                    Toast.makeText(user_Login.this,"Login Failed! Please try again", Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();
            }
        });
    }
}
