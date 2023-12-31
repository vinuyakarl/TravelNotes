package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelnotes.R;
import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.entity.SortOption;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.example.travelnotes.main.fragments.AddTripFragment;
import com.example.travelnotes.main.fragments.SortTripFragment;
import com.example.travelnotes.main.fragments.UserProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SortTripFragment.OnSortOptionSelectedListener, AddTripFragment.OnTripAddedListener{
    private TripAdapter tripAdapter;
    private ListView tripListView;
    private TripManager tripManager;
    private final UserManager userManager = UserManager.getInstance();
    private final User currentUser = userManager.getCurrentUser();
    private Button addButton;
    private Button logoutButton;
    private Button sortButton;
    private Button profileButton;
    private SearchView searchBar;
    private Trip selectedTrip;

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
        tripAdapter.sortTripsList(null, null);
        tripListView.setAdapter(tripAdapter);
        tripListView.setOnItemClickListener((parent, view, position, id) -> tripListPressed(position));

        getUIElements();
        addButton.setOnClickListener(v -> addButtonPressed());
        logoutButton.setOnClickListener(v -> logoutButtonPressed());
        sortButton.setOnClickListener(v -> sortButtonPressed());
        profileButton.setOnClickListener(v -> profileButtonPressed());
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });
    }


    private void getUIElements() {
        addButton = findViewById(R.id.addIcon);
        logoutButton = findViewById(R.id.logoutButton);
        sortButton = findViewById(R.id.sortButton);
        profileButton = findViewById(R.id.profileIcon);
        searchBar = findViewById(R.id.homePageSearch);
    }

    private void tripListPressed(int position) {
        selectedTrip = tripAdapter.getItem(position);
        Intent intent = new Intent(getApplicationContext(), ViewTripActivity.class);
        intent.putExtra("selectedTrip", selectedTrip);
        startActivity(intent);
    }

    private void addButtonPressed() {
        AddTripFragment addTripFragment = new AddTripFragment();
        addTripFragment.show(getSupportFragmentManager(), "ADD_TRIP");
    }

    private void logoutButtonPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(this, currentUser.getUsername() + " Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void sortButtonPressed() {
        SortTripFragment sortTripFragment = new SortTripFragment();
        sortTripFragment.show(getSupportFragmentManager(), "SORT_TRIP");
    }

    private void profileButtonPressed() {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        userProfileFragment.show(getSupportFragmentManager(), "USER_PROFILE");
    }

    @Override
    public void onSortOptionSelected(SortOption chosenSort, String sortDirection) {
        tripAdapter.sortTripsList(chosenSort.getSortType(), sortDirection);
    }

    @Override
    public void onTripAdded() {
        tripAdapter.sortTripsList(null, null);
        tripAdapter.setTrips(tripManager.getTrips());
        tripListPressed(tripAdapter.getCount() - 1);
    }

    public void performSearch(String query) {
        ArrayList<Trip> searchedTrips = new ArrayList<>();
        for (Trip trip: tripManager.getTrips()) {
            if (trip.matchesQuery(query)) {
                searchedTrips.add(trip);
            }
        }
        tripAdapter.setTrips(searchedTrips);
        tripAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent resumeIntent = getIntent();
        if (resumeIntent.hasExtra("toBeDel")) {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            selectedTrip = (Trip) resumeIntent.getSerializableExtra("toBeDel");
            tripManager.deleteTrip(selectedTrip);
            ArrayList<Trip> newTrips = new ArrayList<>();
            for (Trip trip: tripManager.getTrips()) {
                if (!trip.getUniqueId().equals(selectedTrip.getUniqueId())) {
                    newTrips.add(trip);
                }
            }
            tripManager.setTrips(newTrips);
            tripAdapter.setTrips(newTrips);
            tripAdapter.notifyDataSetChanged();
        }
    }
}