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
        tripCollection = db.collection("itineraries");
    }

    public void addItineraryToTripDB(Trip trip, Itinerary itinerary) {
        tripCollection.document(trip.getUniqueId().toString()).set(itinerary)
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Itinerary successfully written!"))
                .addOnFailureListener(e -> Log.w("Firebase", "Error writing itinerary", e));
    }
}
