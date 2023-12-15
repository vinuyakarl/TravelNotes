package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelnotes.R;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getUIElements();
        addButtonListeners();
    }

    private void getUIElements() {
        loginButton = findViewById(R.id.loginButton);
    }

    private void goToHomePage() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void loginButtonPressed() {
        goToHomePage();
    }

    private void addButtonListeners() {
        loginButton.setOnClickListener(v -> goToHomePage());
    }
}