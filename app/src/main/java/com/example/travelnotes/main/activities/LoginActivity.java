package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.SignUpFragment;

/**
 * LoginActivity where users can login, click on a button to signup, etc.
 */
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

    /**
     * Gets the UI elements in the loginActivity
     */
    private void getUIElements() {
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        usernameField = findViewById(R.id.editTextLoginUser);
        passwordField = findViewById(R.id.editTextLoginPassword);
    }

    /**
     * Goes to homepage
     */
    private void goToHomePage() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Button listener when login button is pressed, three cases:
     *      valid login, wrong password, not existing user
     */
    private void loginButtonPressed() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        User user = userManager.getUserByUsername(username);

        // If user exists and password is correct
        if(userManager.containsUser(username) && password.equals(user.getPassword())) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            userManager.setCurrentUser(user);
            goToHomePage();
        }

        // If password is incorrect
        else if (userManager.containsUser(username) && !password.equals(user.getPassword())) {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }

        // If password is wrong
        else if (!userManager.containsUser(username)) {
            Toast.makeText(this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Button listener when user presses on signup button
     */
    private void signupButtonPressed() {
        signUpFragment.show(getSupportFragmentManager(), "signup");
    }

    /**
     * add button listeners to the corresponding functions
     */
    private void addButtonListeners() {
        loginButton.setOnClickListener(v -> loginButtonPressed());
        signupButton.setOnClickListener(v -> signupButtonPressed());
    }

}