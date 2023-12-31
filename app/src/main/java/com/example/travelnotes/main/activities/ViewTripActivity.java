package com.example.travelnotes.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.travelnotes.R;
import com.example.travelnotes.main.adapters.ItineraryAdapter;
import com.example.travelnotes.main.entity.Itinerary;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.fragments.AddItineraryFragment;
import com.example.travelnotes.main.fragments.EditItineraryFragment;
import com.example.travelnotes.main.fragments.EditTripFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * This activity is displayed when a user has clicked on a trip from the homepage. The selected trip
 * and its properties, itineraries are displayed here. Users can also choose to edit the trip,
 * delete, edit, or add itineraries, or delete the trip itself here.
 */
public class ViewTripActivity extends AppCompatActivity implements AddItineraryFragment.DialogCloseListener, EditItineraryFragment.EditDialogCloseListener {
    private AddItineraryFragment addItineraryFragment;
    private Trip selectedTrip;
    private TextView destinationViewText;
    private TextView originViewText;
    private TextView dateViewText;
    private TextView costViewText;
    private Button doneButton;
    private Button deleteButton;
    private Button editButton;
    private Button addItineraryButton;
    private ItineraryAdapter itineraryAdapter;
    private ListView itineraryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");
        itineraryAdapter = new ItineraryAdapter(this, selectedTrip.getItineraries());
        itineraryAdapter.sortItineraryList();
        itineraryListView = findViewById(R.id.itineraryListView);
        itineraryListView.setAdapter(itineraryAdapter);
        itineraryListView.setOnItemClickListener((parent, view, position, id) -> itineraryClicked(position));

        getUIElements();
        setTexts();
        addButtonListeners();
    }

    /**
     * Get UI elements in ViewTripActivity
     */
    public void getUIElements() {
        destinationViewText = findViewById(R.id.destinationViewText);
        originViewText = findViewById(R.id.originViewText);
        dateViewText = findViewById(R.id.dateViewText);
        costViewText = findViewById(R.id.costViewText);
        doneButton = findViewById(R.id.doneButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        addItineraryButton = findViewById(R.id.addItineraryButton);
    }

    /**
     * Add corresponding button listeners to the UI elements
     */
    public void addButtonListeners() {
        addItineraryButton.setOnClickListener(v -> addItineraryButtonClicked());
        doneButton.setOnClickListener(v -> doneButtonClicked());
        deleteButton.setOnClickListener(v -> deleteButtonClicked());
        editButton.setOnClickListener(v -> editButtonClicked());
    }

    /**
     * Set the texts in the UI based on the selected trip
     */
    public void setTexts() {
        destinationViewText.setText(selectedTrip.getDestination());
        originViewText.setText(selectedTrip.getOrigin());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formattedTripStart = dateFormat.format(selectedTrip.getTripStarted());
        String formattedTripEnded = dateFormat.format(selectedTrip.getTripEnded());
        dateViewText.setText(formattedTripStart + " - " + formattedTripEnded);
        costViewText.setText(String.format("$%.2f", selectedTrip.getCost()));
    }

    /**
     * Button listener when the add itinerary button is clicked, would display the add
     * itinerary fragment
     */
    public void addItineraryButtonClicked() {
        addItineraryFragment = new AddItineraryFragment(selectedTrip);
        addItineraryFragment.setDialogCloseListener(this);
        addItineraryFragment.show(getSupportFragmentManager(), "ADD_ITINERARY");
    }

    /**
     * When user is done adding a new itinerary, sort the itinerary list based on date, as well as
     * update the cost of trip
     */
    @Override
    public void onDialogClosed() {
        itineraryAdapter.sortItineraryList();
        costViewText.setText(String.format("$%.2f", selectedTrip.getCost()));
    }

    /**
     * When user is done editing an itinerary, notify that the data set has changed, as well as re-sorting
     * list and updating cost
     */
    @Override
    public void onEditDialogClosed() {
        itineraryAdapter.notifyDataSetChanged();
        itineraryAdapter.sortItineraryList();
        costViewText.setText(String.format("$%.2f", selectedTrip.getCost()));
    }

    /**
     * Button listener when done button is clicked. Goes back to homepage
     */
    public void doneButtonClicked() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Button listener when delete button is clicked. Goes back to homepage and lets homepage
     * know that user wants the selected trip to be deleted
     */
    public void deleteButtonClicked() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("toBeDel", selectedTrip);
        startActivity(intent);
    }

    /**
     * Button listener when edit button is clicked. Opens up edit trip fragment where user
     * can edit some properties of the trip
     */
    public void editButtonClicked() {
        EditTripFragment editTripFragment = new EditTripFragment(selectedTrip);
        editTripFragment.show(getSupportFragmentManager(), "EDIT_TRIP");
    }

    /**
     * Button listener when an itinerary is clicked. Opens up an edit itinerary fragment where user
     * can edit the itinerary properties
     * @param position: position of the selected itinerary in the itinerary list view
     */
    public void itineraryClicked(int position) {
        Itinerary selectedItinerary = itineraryAdapter.getItem(position);
        EditItineraryFragment editItineraryFragment = new EditItineraryFragment(selectedTrip, selectedItinerary);
        editItineraryFragment.setDialogCloseListener(this);
        editItineraryFragment.show(getSupportFragmentManager(), "EDIT_ITINERARY");

    }
}