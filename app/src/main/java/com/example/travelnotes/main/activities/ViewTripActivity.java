package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.AddItineraryFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewTripActivity extends AppCompatActivity {
    private AddItineraryFragment addItineraryFragment = new AddItineraryFragment();
    private Trip selectedTrip;
    private TextView destinationViewText;
    private TextView originViewText;
    private TextView dateViewText;
    private TextView costViewText;
    private Button doneButton;
    private Button addItineraryButton;
    private Button logoutButton;
    private User currentUser = UserManager.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");

        getUIElements();
        setTexts();
        addItineraryButton.setOnClickListener(v -> addItineraryButtonClicked());
    }

    public void getUIElements() {
        destinationViewText = findViewById(R.id.destinationViewText);
        originViewText = findViewById(R.id.originViewText);
        dateViewText = findViewById(R.id.dateViewText);
        costViewText = findViewById(R.id.costViewText);
        doneButton = findViewById(R.id.doneButton);
        addItineraryButton = findViewById(R.id.addItineraryButton);
    }

    public void setTexts() {
        destinationViewText.setText(selectedTrip.getDestination());
        originViewText.setText(selectedTrip.getOrigin());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formattedTripStart = dateFormat.format(selectedTrip.getTripStarted());
        String formattedTripEnded = dateFormat.format(selectedTrip.getTripEnded());
        dateViewText.setText(formattedTripStart + " - " + formattedTripEnded);
    }

    private void addItineraryButtonClicked() {
        addItineraryFragment.show(getSupportFragmentManager(), "ADD_ITINERARY");
    }
}