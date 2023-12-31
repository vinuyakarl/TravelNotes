package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


/**
 * DB functions relating to users are located here. Functions for adding a user to DB, fetching users
 * from DB are located in this class.
 */
public class UserDB {
    private final CollectionReference userCollection;
    private final FirebaseFirestore db;

    public UserDB() {
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");
    }

    /**
     * Adds a user in DB
     * @param user: user to be added
     */
    public void addUserDB(User user) {
        userCollection.document(user.getUsername()).set(user)
                .addOnSuccessListener(aVoid -> Log.d("LoginActivity", "Username successfully written!"))
                .addOnFailureListener(e -> Log.w("LoginActivity", "Error writing username", e));
    }

    /**
     * Initial fetch of users, trips and itineraries
     */
    public void fetchUsersDB() {
        UserManager userManager = UserManager.getInstance();
        ArrayList<User> usersDB = new ArrayList<>();
        userCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot userDoc: queryDocumentSnapshots) {
                        User user = userDoc.toObject(User.class);
                        usersDB.add(user);
                        ArrayList<Trip> tripsDB = new ArrayList<>();
                        userDoc.getReference().collection("trips").get()
                                .addOnSuccessListener(tripsDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot tripDoc: tripsDocumentSnapshots) {
                                        Trip trip = tripDoc.toObject(Trip.class);
                                        tripsDB.add(trip);
                                        Log.d("Firebase", trip.getOrigin() + " successfully fetched");
                                    }
                                    user.getTripManager().setTrips(tripsDB);
                                    user.getTripManager().fetchItineraries();
                                });
                    }
                    userManager.setUsers(usersDB);
                });
    }
}
