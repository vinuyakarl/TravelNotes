package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.travelnotes.main.activities.ViewTripActivity;
import com.example.travelnotes.main.entity.Itinerary;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This fragment is used when user wants to edit an existing itinerary that corresponds to a trip
 */
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

    /**
     * Interface used to let ViewItemActivity know that an itinerary has been updated
     */
    public interface EditDialogCloseListener {
        void onEditDialogClosed();
    }

    private EditItineraryFragment.EditDialogCloseListener listener;

    public void setDialogCloseListener(EditItineraryFragment.EditDialogCloseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {
            listener.onEditDialogClosed();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_itinerary, null);

        getUIElements(view);
        setUITexts();
        addButtonListeners();

        builder.setView(view);
        return builder.create();
    }

    public EditItineraryFragment(Trip trip, Itinerary itinerary) {
        this.selectedTrip = trip;
        this.selectedItinerary = itinerary;
    }

    /**
     * Gets UI Elements for EditItineraryFragment
     * @param view: view of the fragment
     */
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

    /**
     * Adds button listeners to the corresponding UI elements
     */
    private void addButtonListeners() {
        cancelButton.setOnClickListener(v -> cancelButtonClicked());
        dateButton.setOnClickListener(v -> dateButtonClicked());
        startTimeButton.setOnClickListener(v -> timeButtonClicked(startTimeButton));
        endTimeButton.setOnClickListener(v -> timeButtonClicked(endTimeButton));
        confirmButton.setOnClickListener(v -> confirmButtonClicked());
    }

    /**
     * Sets the UI texts based on the properties of the existing itinerary
     */
    private void setUITexts() {
        titleTextView.setText("Edit Itinerary");
        cancelButton.setText("DELETE");
        addActivity.setText(selectedItinerary.getActivity());
        addLocation.setText(selectedItinerary.getLocation());
        timeStarted = LocalTime.parse(selectedItinerary.getTimeStart());
        startTimeButton.setText(selectedItinerary.getTimeStart());
        timeEnded = LocalTime.parse(selectedItinerary.getTimeEnd());
        endTimeButton.setText(selectedItinerary.getTimeEnd());

        addCost.setText(String.format("%.2f", selectedItinerary.getCost()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        activityDate = selectedItinerary.getDate();
        String formattedDate = dateFormat.format(activityDate);
        dateButton.setText(formattedDate);


    }

    /**
     * Button listener when delete button is clicked. Deletes the selected itinerary
     */
    private void cancelButtonClicked() {
        selectedTrip.deleteItinerary(selectedItinerary);
        currentUser.getTripManager().fetchItineraries();
        dismiss();
    }

    /**
     * Button listener when the date button is clicked. User can select the date of when the itinerary
     * is taking place, which would be stored with the itinerary itself
     */
    private void dateButtonClicked() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    newDate.set(year, monthOfYear, dayOfMonth, 0, 0);
                    activityDate = newDate.getTime();



                    if (activityDate.after(selectedTrip.getTripEnded()) || activityDate.before(selectedTrip.getTripStarted())) {
                        Toast.makeText(getContext(), "Select a Valid Date Within Trip Range", Toast.LENGTH_SHORT).show();
                        dateButtonClicked();
                    }

                    else {
                        String formattedDate = dateFormat.format(activityDate);
                        dateButton.setText(formattedDate);
                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    /**
     * Checks if the user inputs are valid and can be added
     * @param activity: inputted itinerary activity
     * @param location: inputted itinerary location
     * @param cost: inputted itinerary cost
     * @return boolean: if inputs are valid or not
     */
    private boolean isInputValid(String activity, String location, String cost) {
        boolean anyFieldsEmpty = activity.isEmpty() || location.isEmpty() || cost.isEmpty();
        boolean anyTimeEmpty = timeStarted == null || timeEnded == null || activityDate == null;

        // Check if any fields is empty
        if (anyFieldsEmpty || anyTimeEmpty) {
            Toast.makeText(getContext(), "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if trip date is valid
        if (timeStarted.isAfter(timeEnded)) {
            Toast.makeText(getContext(), "Invalid Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        // If activityDate is before starting date OR activityDate is after trip end
        Log.d("valid", String.valueOf(activityDate.before(selectedTrip.getTripStarted())));
        if (activityDate.before(selectedTrip.getTripStarted()) || activityDate.after(selectedTrip.getTripEnded())) {
            Toast.makeText(getContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Button listener when the time buttons are clicked. Users can select the time of when the itinerary
     * starts or ends, which would be stored in the itinerary itself
     * @param timeButton: which timeButton was pressed (startTime or endTime)
     */
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

    /**
     * Button listener for confirm button. Edits the old itinerary to a new one based on the newly
     * updated properties. If no properties were changed, nothing changes.
     */
    private void confirmButtonClicked() {
        String activity = addActivity.getText().toString();
        String location = addLocation.getText().toString();
        String cost = addCost.getText().toString();

        boolean isValid = isInputValid(activity, location, cost);
        if (isValid) {
            Itinerary newItinerary = new Itinerary(activityDate, timeStarted.toString(), timeEnded.toString(), location, activity, Float.parseFloat(cost));
            selectedTrip.editItinerary(selectedItinerary, newItinerary);
            Toast.makeText(getContext(), "Itinerary Edited", Toast.LENGTH_SHORT).show();
            currentUser.getTripManager().fetchItineraries();
            dismiss();
        }
    }
}
