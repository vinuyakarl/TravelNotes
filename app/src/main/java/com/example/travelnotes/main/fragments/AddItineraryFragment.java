package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddItineraryFragment extends DialogFragment {
    private Trip selectedTrip;
    private Button cancelButton;
    private Button confirmButton;
    private EditText addActivity;
    private EditText addLocation;
    private EditText addCost;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button dateButton;
    private LocalTime timeStarted;
    private LocalTime timeEnded;
    private Date activityDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_itinerary, null);

        getUIElements(view);
        cancelButton.setOnClickListener(v -> cancelButtonClicked());
        dateButton.setOnClickListener(v -> dateButtonClicked());
        startTimeButton.setOnClickListener(v -> timeButtonClicked(startTimeButton));

        builder.setView(view);
        return builder.create();
    }

    public AddItineraryFragment(Trip trip) {
        this.selectedTrip = trip;
    }

    private void getUIElements(View view) {
        cancelButton = view.findViewById(R.id.cancelButton);
        confirmButton = view.findViewById(R.id.confirmButton);
        addActivity = view.findViewById(R.id.editTextOrigin);
        addLocation = view.findViewById(R.id.editTextLocation);
        addCost = view.findViewById(R.id.editTextCost);
        startTimeButton = view.findViewById(R.id.timeStartedButton);
        endTimeButton = view.findViewById(R.id.timeEndedButton);
        dateButton = view.findViewById(R.id.dateButton);
    }

    private void cancelButtonClicked() {
        dismiss();
    }
    
    private void confirmButtonClicked() {
        String activity = addActivity.getText().toString();
        String location = addLocation.getText().toString();
        String cost = String.valueOf(addCost.getAlpha());
    }


    private void dateButtonClicked() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    newDate.set(year, monthOfYear, dayOfMonth);
                    activityDate = newDate.getTime();

                    if (activityDate.after(selectedTrip.getTripEnded()) || activityDate.before(selectedTrip.getTripStarted())) {
                        Toast.makeText(getContext(), "Select a Valid Date Within Trip Range", Toast.LENGTH_SHORT).show();
                        dateButtonClicked();
                    }

                    String formattedDate = dateFormat.format(activityDate);
                    dateButton.setText(formattedDate);

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void timeButtonClicked(Button timeButton) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minuteOfHour) -> {

                    if (timeButton == startTimeButton) {
                        timeStarted = LocalTime.of(hourOfDay, minuteOfHour);
                        timeButton.setText(timeStarted.toString());
                    }
                    else {
                        timeEnded = LocalTime.of(hourOfDay, minuteOfHour);
                        timeButton.setText(timeEnded.toString());
                    }

                }, hour, minute, DateFormat.is24HourFormat(getContext()));

        timePickerDialog.show();
    }
}
