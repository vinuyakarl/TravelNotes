package com.example.travelnotes.main.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.SignUpFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signupButton;
    private EditText usernameField;
    private EditText passwordField;
    private SignUpFragment signUpFragment = new SignUpFragment();
    private UserManager userManager = UserManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager.fetchUsers();
        getUIElements();
        addButtonListeners();

    }

    private void getUIElements() {
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        usernameField = findViewById(R.id.editTextLoginUser);
        passwordField = findViewById(R.id.editTextLoginPassword);
    }

    private void goToHomePage() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void loginButtonPressed() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        if(userManager.containsUser(username) && password.equals(userManager.))
        goToHomePage();
    }

    private void signupButtonPressed() {
        signUpFragment.show(getSupportFragmentManager(), "signup");
    }

    private void addButtonListeners() {
        loginButton.setOnClickListener(v -> loginButtonPressed());
        signupButton.setOnClickListener(v -> signupButtonPressed());
    }

}