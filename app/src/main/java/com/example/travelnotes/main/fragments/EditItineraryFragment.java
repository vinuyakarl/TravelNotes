package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Itinerary;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditItineraryFragment extends DialogFragment {
    private Itinerary selectedItinerary;
    private Trip selectedTrip;
    private Button cancelButton;
    private Button confirmButton;
    private TextView titleTextView;
    private EditText addActivity;
    private EditText addLocation;
    private EditText addCost;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button dateButton;
    private LocalTime timeStarted;
    private LocalTime timeEnded;
    private Date activityDate;
    private User currentUser = UserManager.getInstance().getCurrentUser();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_itinerary, null);

        getUIElements(view);
        setUITexts();
        cancelButton.setOnClickListener(v -> cancelButtonClicked());

        builder.setView(view);
        return builder.create();
    }

    public EditItineraryFragment(Trip trip, Itinerary itinerary) {
        this.selectedTrip = trip;
        this.selectedItinerary = itinerary;
    }

    private void getUIElements(View view) {
        titleTextView = view.findViewById(R.id.titleTextView);
        cancelButton = view.findViewById(R.id.cancelButton);
        confirmButton = view.findViewById(R.id.confirmButton);
        addActivity = view.findViewById(R.id.editTextActivity);
        addLocation = view.findViewById(R.id.editTextLocation);
        addCost = view.findViewById(R.id.editTextCost);
        startTimeButton = view.findViewById(R.id.timeStartedButton);
        endTimeButton = view.findViewById(R.id.timeEndedButton);
        dateButton = view.findViewById(R.id.dateButton);
    }

    private void setUITexts() {
        titleTextView.setText("Edit Itinerary");
        addActivity.setText(selectedItinerary.getActivity());
        addLocation.setText(selectedItinerary.getLocation());
        startTimeButton.setText(selectedItinerary.getTimeStart());
        endTimeButton.setText(selectedItinerary.getTimeEnd());

        addCost.setText(selectedItinerary.getCost().toString());

    }

    private void cancelButtonClicked() {
        dismiss();
    }
}
