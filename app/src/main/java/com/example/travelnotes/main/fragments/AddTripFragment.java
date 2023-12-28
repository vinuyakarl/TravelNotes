package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTripFragment extends DialogFragment {
    private Button cancelButton;
    private Button confirmButton;
    private EditText addOrigin;
    private EditText addDestination;
    private Button startDateButton;
    private Button endDateButton;
    private Date tripStarted;
    private Date tripEnded;
    private TripManager currentTripManager;
    private OnTripAddedListener tripAddedListener;

    public interface OnTripAddedListener {
        void onTripAdded();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            tripAddedListener = (OnTripAddedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTripAddedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_trip, null);

        currentTripManager = UserManager.getInstance().getCurrentUser().getTripManager();

        getUIElements(view);
        startDateButton.setOnClickListener(v -> dateButtonClicked(startDateButton));
        endDateButton.setOnClickListener(v -> dateButtonClicked(endDateButton));
        cancelButton.setOnClickListener(v -> cancelButtonClicked());
        confirmButton.setOnClickListener(v -> confirmButtonClicked());

        builder.setView(view);
        return builder.create();
    }

    private void getUIElements(View view) {
        cancelButton = view.findViewById(R.id.cancelButton);
        confirmButton = view.findViewById(R.id.confirmButton);
        addOrigin = view.findViewById(R.id.editTextOrigin);
        addDestination = view.findViewById(R.id.editTextDestination);
        startDateButton = view.findViewById(R.id.dateStartedButton);
        endDateButton = view.findViewById(R.id.dateEndedButton);
    }

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
     * (I made my own negativeButton due to wanting to add a border
     * to the fragment)
     */
    private void cancelButtonClicked() {
        dismiss();
    }


    private void confirmButtonClicked() {
        String origin = addOrigin.getText().toString();
        String destination = addDestination.getText().toString();

        boolean isValid = isInputValid(origin, destination);
        if (isValid) {
            Trip newTrip = new Trip(destination, origin, tripStarted, tripEnded, 0.00f);
            currentTripManager.addTrip(newTrip);
            if (tripAddedListener != null) {
                tripAddedListener.onTripAdded();
            }
            Toast.makeText(getContext(), "Trip Added", Toast.LENGTH_SHORT).show();
            dismiss();

        }
    }

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
}
