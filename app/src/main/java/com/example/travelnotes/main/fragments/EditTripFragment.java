package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.travelnotes.main.activities.ViewTripActivity;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment used when user clicks on edit button in ViewTripActivity.
 */
public class EditTripFragment extends DialogFragment {
    private Trip selectedTrip;
    private TextView titleTextView;
    private Button cancelButton;
    private Button confirmButton;
    private EditText addOrigin;
    private EditText addDestination;
    private Button startDateButton;
    private Button endDateButton;
    private Date tripStarted;
    private Date tripEnded;
    private TripManager currentTripManager;

    public EditTripFragment(Trip trip) {
        this.selectedTrip = trip;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_trip, null);

        currentTripManager = UserManager.getInstance().getCurrentUser().getTripManager();

        getUIElements(view);
        setUITexts();
        addButtonListeners();

        builder.setView(view);
        return builder.create();
    }

    /**
     * Gets UI elements for EditTripFragment
     * @param view: view of fragment
     */
    private void getUIElements(View view) {
        titleTextView = view.findViewById(R.id.titleTextView);
        cancelButton = view.findViewById(R.id.cancelButton);
        confirmButton = view.findViewById(R.id.confirmButton);
        addOrigin = view.findViewById(R.id.editTextOrigin);
        addDestination = view.findViewById(R.id.editTextDestination);
        startDateButton = view.findViewById(R.id.dateStartedButton);
        endDateButton = view.findViewById(R.id.dateEndedButton);
    }

    /**
     * Adds button listeners to corresponding UI elements
     */
    private void addButtonListeners() {
        cancelButton.setOnClickListener(v -> cancelButtonClicked());
        startDateButton.setOnClickListener(v -> dateButtonClicked(startDateButton));
        endDateButton.setOnClickListener(v -> dateButtonClicked(endDateButton));
        confirmButton.setOnClickListener(v -> confirmButtonClicked());
    }

    /**
     * Sets UI texts based on the selected trip properties
     */
    private void setUITexts() {
        titleTextView.setText("Edit Trip");
        addOrigin.setText(selectedTrip.getOrigin());
        addDestination.setText(selectedTrip.getDestination());

        tripStarted = selectedTrip.getTripStarted();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tripStart = dateFormat.format(tripStarted);
        startDateButton.setText(tripStart);

        tripEnded = selectedTrip.getTripEnded();
        String tripEnd = dateFormat.format(tripEnded);
        endDateButton.setText(tripEnd);

    }

    /**
     * Button listener when the date button is clicked. User can select the date of when the trip
     * is taking place, which would be stored with the trip itself
     * @param dateButton: which dateButton was pressed (tripStart, tripEnd)
     */
    private void dateButtonClicked(Button dateButton) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    if (dateButton == startDateButton) {
                        newDate.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                        tripStarted = newDate.getTime();
                        String formattedTripStart = dateFormat.format(tripStarted);
                        dateButton.setText(formattedTripStart);
                    }
                    else {
                        newDate.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
                        tripEnded = newDate.getTime();
                        String formattedTripEnd = dateFormat.format(tripEnded);
                        dateButton.setText(formattedTripEnd);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    /**
     * Closes the fragment when cancel button is pressed
     */
    private void cancelButtonClicked() {
        dismiss();
    }

    /**
     * Button listener when user has clicked the confirm button. If all inputs are valid, would edit trip
     * and update the trip in the TripManager. Opens up the newly edited trip
     */
    private void confirmButtonClicked() {
        String origin = addOrigin.getText().toString();
        String destination = addDestination.getText().toString();

        boolean isValid = isInputValid(origin, destination);
        if (isValid) {
            Trip newTrip = new Trip(destination, origin, tripStarted, tripEnded, 0.00f);
            currentTripManager.editTrip(selectedTrip, newTrip);
            currentTripManager.fetchTrips();
            Toast.makeText(getContext(), "Trip Edited", Toast.LENGTH_SHORT).show();
            dismiss();
            updateViewTripActivity();

        }
    }

    /**
     * Checks if inputs are valid
     * @param origin: inputted trip origin
     * @param destination: inputted trip destination
     * @return boolean: if inputs are valid or not
     */
    private boolean isInputValid(String origin, String destination) {
        boolean anyFieldsEmpty = origin.isEmpty() || destination.isEmpty();
        boolean anyDatesEmpty = tripStarted == null || tripEnded == null;

        // Check if any fields is empty
        if (anyFieldsEmpty || anyDatesEmpty) {
            Toast.makeText(getContext(), "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if trip date is valid
        if (tripStarted.compareTo(tripEnded) == 1) {
            Toast.makeText(getContext(), "Invalid Trip Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * When a trip is edited, opens up a new ViewTripActivity which would display newly edited properties
     */
    private void updateViewTripActivity() {
        Intent intent = new Intent(getContext(), ViewTripActivity.class);
        intent.putExtra("selectedTrip", selectedTrip);
        if (getActivity() != null) {
            getActivity().finish(); // Close the current activity
        }
        startActivity(intent);
    }
}
