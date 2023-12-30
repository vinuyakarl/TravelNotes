package com.example.travelnotes.main.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class TripManagerDB {
    private FirebaseFirestore db;
    private CollectionReference userCollection;
    private UserManager userManager;

    public TripManagerDB() {
        this.db = FirebaseFirestore.getInstance();
        this.userCollection = db.collection("users");
        this.userManager = UserManager.getInstance();
    }
    public void addTripToDB(Trip trip) {
        User currentUser = userManager.getCurrentUser();
        CollectionReference tripCollection = userCollection.document(currentUser.getUsername()).collection("trips");
        tripCollection.document(trip.getUniqueId().toString())
                .set(trip)
                .addOnSuccessListener(unused -> Log.d("Firestore", String.format("Trip %s Added!", trip.getDestination())))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding trip", e));
    }

    public void deleteTripFromDB(Trip trip) {
        User currentUser = userManager.getCurrentUser();
        CollectionReference tripCollection = userCollection.document(currentUser.getUsername()).collection("trips");
        tripCollection.document(trip.getUniqueId()).delete().addOnSuccessListener(unused -> Log.d("Deleting", "Successfully deleted " + trip.getDestination()));
    }

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
