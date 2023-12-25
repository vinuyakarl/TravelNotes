package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.entity.Itinerary;
import com.example.travelnotes.main.entity.Trip;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ItineraryDB {
    private final CollectionReference tripCollection;
    private final FirebaseFirestore db;

    public ItineraryDB() {
        db = FirebaseFirestore.getInstance();
        tripCollection = db.collection("trips");
    }

    public void addItineraryToTripDB(Trip trip, Itinerary itinerary) {
        CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId().toString()).collection("itineraries");
        itineraryCollection.document(itinerary.getUniqueID().toString())
                .set(itinerary)
                .addOnSuccessListener(unused -> Log.d("Firestore", String.format("Itinerary added!", trip.getDestination())))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding itinerary", e));
    }
}
