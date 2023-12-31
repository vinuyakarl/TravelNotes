package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;

/**
 * This fragment is used when user icon is clicked in homepage. Displays username of user.
 */
public class UserProfileFragment extends DialogFragment {
    TextView userTextView;
    Button closeButton;
    User currentUser = UserManager.getInstance().getCurrentUser();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_profile, null);

        getUIElements(view);
        userTextView.setText(currentUser.getUsername());
        closeButton.setOnClickListener(v -> dismiss());
        builder.setView(view);
        return builder.create();
    }

    /**
     * Gets UI Elements for UserProfileFragment
     * @param view: view of fragment
     */
    public void getUIElements(View view) {
        userTextView = view.findViewById(R.id.usernameViewText);
        closeButton = view.findViewById(R.id.doneButton);
    }

}
