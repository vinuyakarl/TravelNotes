package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TripManagerDB {
    private FirebaseFirestore db;
    private CollectionReference userCollection;
    private UserManager userManager;
    private User currentUser;

    public TripManagerDB() {
        db = FirebaseFirestore.getInstance();
        userManager = UserManager.getInstance();
        currentUser = userManager.getCurrentUser();
        userCollection = db.collection("users");

    }
    public void addTripToDB(Trip trip) {
        CollectionReference tripCollection = userCollection.document(currentUser.getUsername()).collection("trips");
        tripCollection.document(trip.getUniqueId().toString())
                .set(trip)
                .addOnSuccessListener(unused -> {
                    Log.d("Firestore", String.format("Trip %s Added!", trip.getDestination()));
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding trip", e);
                });
    }

}
