package com.example.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText etRegisterName, etRegisterEmail, etRegisterPassword;
    private FirebaseAuth authentication;
    private String email,password;
    private Button btnUserRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authentication = FirebaseAuth.getInstance();
        findViewById();
        userRegistrations();
    }

    private void findViewById() {
        etRegisterName = findViewById(R.id.etRegisterName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnUserRegister = findViewById(R.id.btnUserRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void userRegistrations() {

        btnUserRegister.setOnClickListener(view -> validateUser());
        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void validateUser() {

        String name = etRegisterName.getText().toString();
        email = etRegisterEmail.getText().toString();
        password = etRegisterPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, Constant.Please_Fill_All_Fields, Toast.LENGTH_SHORT).show();
        } else {
            registerUser();
        }
    }

    private void registerUser() {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                (this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, Constant.
                                User_Registration_Successful, Toast.LENGTH_SHORT).show();
                        authentication.signOut();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, Constant.Error + Objects
                                .requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }
}