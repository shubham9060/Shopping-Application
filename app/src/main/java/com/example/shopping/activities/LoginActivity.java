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

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginEmail, etLoginPassword;
    private TextView tvRegister;
    private String email, password;
    private FirebaseAuth authentication;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        authentication = FirebaseAuth.getInstance();
        Register();
        btnLogin.setOnClickListener(view -> validateUser());
    }

    private void findViewById() {
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void Register() {
        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authentication.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void validateUser() {
        email = etLoginEmail.getText().toString();
        password = etLoginPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, Constant.Please_Fill_All_Fields, Toast.LENGTH_SHORT).show();
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, Constant.Login_Success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, Constant.Error + Objects.
                        requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}