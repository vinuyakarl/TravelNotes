package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * DB functions relating to trips are located here. Functions for adding a trip to DB, deleting a trip
 * in DB, editing a trip in DB, and fetching trips from DB are located in this class.
 */
public class TripManagerDB {
    private FirebaseFirestore db;
    private CollectionReference userCollection;
    private UserManager userManager;

    public TripManagerDB() {
        this.db = FirebaseFirestore.getInstance();
        this.userCollection = db.collection("users");
        this.userManager = UserManager.getInstance();
    }

    /**
     * Adds a trip to DB
     * @param trip: trip to be added
     */
    public void addTripToDB(Trip trip) {
        User currentUser = userManager.getCurrentUser();
        CollectionReference tripCollection = userCollection.document(currentUser.getUsername()).collection("trips");
        tripCollection.document(trip.getUniqueId())
                .set(trip)
                .addOnSuccessListener(unused -> Log.d("Firestore", String.format("Trip %s Added!", trip.getDestination())))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding trip", e));
    }

    /**
     * Deletes a trip from DB
     * @param trip: trip to be deleted
     */
    public void deleteTripFromDB(Trip trip) {
        User currentUser = userManager.getCurrentUser();
        CollectionReference tripCollection = userCollection.document(currentUser.getUsername()).collection("trips");
        tripCollection.document(trip.getUniqueId()).delete().addOnSuccessListener(unused -> Log.d("Deleting", "Successfully deleted " + trip.getDestination()));
    }

    /**
     * Edits a trip from DB
     * @param oldTrip: trip to be edited
     * @param newTrip: trip that has the newly edited properties
     */
    public void editTripInDB(Trip oldTrip, Trip newTrip) {
        User currentUser = userManager.getCurrentUser();
        DocumentReference tripDocument = userCollection.document(currentUser.getUsername()).collection("trips").document(oldTrip.getUniqueId());
        tripDocument.update("origin", newTrip.getOrigin(),
                "destination", newTrip.getDestination(),
                "tripStarted", newTrip.getTripStarted(),
                "tripEnded", newTrip.getTripEnded())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Trip successfully updated!"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating trip", e));
    }

    public void fetchTripsInDB() {
        User currentUser = userManager.getCurrentUser();
        CollectionReference tripDocument = userCollection.document(currentUser.getUsername()).collection("trips");
        ArrayList<Trip> tripsDB = new ArrayList<>();
        tripDocument.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot tripDoc: queryDocumentSnapshots) {
                        Trip trip =tripDoc.toObject(Trip.class);
                        tripsDB.add(trip);
                    }
                    currentUser.getTripManager().setTrips(tripsDB);
                });
    }

}
