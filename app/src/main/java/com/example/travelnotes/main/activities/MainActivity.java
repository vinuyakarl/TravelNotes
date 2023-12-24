package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelnotes.R;
import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.AddTripFragment;

public class MainActivity extends AppCompatActivity{
    private TripAdapter tripAdapter;
    private ListView tripListView;
    private TripManager tripManager;
    private UserManager userManager = UserManager.getInstance();
    private User currentUser = userManager.getCurrentUser();
    private AddTripFragment addTripFragment = new AddTripFragment();
    private ViewTripActivity viewTripActivity;
    private Button addButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = currentUser.getUsername();

        tripManager = currentUser.getTripManager();

        TextView nameTextView = findViewById(R.id.nameText);
        nameTextView.setText(username);

        tripAdapter = new TripAdapter(this, tripManager.getTrips());
        tripListView = findViewById(R.id.tripListView);
        tripListView.setAdapter(tripAdapter);
        tripListView.setOnItemClickListener((parent, view, position, id) -> tripListPressed(position));

        getUIElements();
        addButton.setOnClickListener(v -> addButtonPressed());
        logoutButton.setOnClickListener(v -> logoutButtonPressed());
    }


    private void getUIElements() {
        addButton = findViewById(R.id.addIcon);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void tripListPressed(int position) {
        Trip selectedTrip = tripManager.getTrip(position);
        Intent intent = new Intent(getApplicationContext(), ViewTripActivity.class);
        intent.putExtra("selectedTrip", selectedTrip);
        startActivity(intent);
    }

    private void addButtonPressed() {
        addTripFragment.show(getSupportFragmentManager(), "ADD_TRIP");
    }

    private void logoutButtonPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, currentUser.getUsername() + " Logged Out", Toast.LENGTH_SHORT).show();
    }
}