package com.example.travelnotes.main.fragments;



import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;


public class SignUpFragment extends DialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private final UserManager userManager = UserManager.getInstance();


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sign_up, null);

        editTextUsername = view.findViewById(R.id.editTextEnterUser);
        editTextPassword = view.findViewById(R.id.editTextEnterPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);

        builder.setView(view)
                .setTitle("Sign Up")
                .setNegativeButton("Cancel", (dialog, id) -> {})
                .setPositiveButton("Confirm", (dialog, id) -> attemptSignup());
        return builder.create();
    }

    private void attemptSignup() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this.getContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
        }
        else if (userManager.containsUser(username)) {
            Toast.makeText(this.getContext(), "User already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            User user = new User(username, password);
            userManager.addUser(user);
            Toast.makeText(this.getContext(), "User Created!", Toast.LENGTH_SHORT).show();
        }
    }

}