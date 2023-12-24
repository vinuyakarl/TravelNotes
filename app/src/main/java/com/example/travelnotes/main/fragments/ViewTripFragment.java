package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewTripFragment extends DialogFragment {
    private Trip selectedTrip;
    private TextView destinationViewText;
    private TextView originViewText;
    private TextView dateViewText;
    private TextView costViewText;
    private Button doneButton;

    public ViewTripFragment(Trip trip) {
        this.selectedTrip = trip;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_view_trip, null);

        getUIElements(view);
        setTexts();

        doneButton.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    public void getUIElements(View view) {
        destinationViewText = view.findViewById(R.id.destinationViewText);
        originViewText = view.findViewById(R.id.originViewText);
        dateViewText = view.findViewById(R.id.dateViewText);
        costViewText = view.findViewById(R.id.costViewText);
        doneButton = view.findViewById(R.id.doneButton);
    }

    public void setTexts() {
        destinationViewText.setText("To: " + selectedTrip.getDestination());
        originViewText.setText("From: " + selectedTrip.getOrigin());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formattedTripStart = dateFormat.format(selectedTrip.getTripStarted());
        String formattedTripEnded = dateFormat.format(selectedTrip.getTripEnded());
        dateViewText.setText("Date: " + formattedTripStart + " - " + formattedTripEnded);
    }
}
