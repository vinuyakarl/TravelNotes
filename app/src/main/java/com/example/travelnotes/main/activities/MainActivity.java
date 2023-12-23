package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.travelnotes.R;
import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.AddTripFragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity{
    private TripAdapter tripAdapter;
    private ListView tripListView;
    private TripManager tripManager;
    private UserManager userManager = UserManager.getInstance();
    private User currentUser = userManager.getCurrentUser();
    private AddTripFragment addTripFragment = new AddTripFragment();
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = currentUser.getUsername();

        tripManager = currentUser.getTripManager();
        Trip trip = new Trip("Edmonton", "Calgary");
        tripManager.addTrip(trip);

        TextView nameTextView = findViewById(R.id.nameText);
        nameTextView.setText(username);

        tripAdapter = new TripAdapter(this, tripManager.getTrips());
        tripListView = findViewById(R.id.tripListView);
        tripListView.setAdapter(tripAdapter);

        getUIElements();
        addButton.setOnClickListener(v -> addButtonPressed());
    }


    private void getUIElements() {
        addButton = findViewById(R.id.addIcon);
    }

    private void addButtonPressed() {
        addTripFragment.show(getSupportFragmentManager(), "add trip");
    }
}