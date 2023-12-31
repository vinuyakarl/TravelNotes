package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.entity.Itinerary;
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

/**
 * DB functions relating to itineraries are located here. The functions for adding itinerary in DB, fetching
 * the itineraries from DB, deleting itineraries in DB, and editing itineraries in DB are located in
 * this class.
 */
public class ItineraryDB {
    private final CollectionReference tripCollection;
    private final FirebaseFirestore db;
    private final User currentUser = UserManager.getInstance().getCurrentUser();

    public ItineraryDB() {
        db = FirebaseFirestore.getInstance();
        tripCollection = db.collection("trips");
    }

    /**
     * Adds a trip itinerary to DB
     * @param trip: trip whose itinerary is being added to
     * @param itinerary: to be added itinerary
     */
    public void addItineraryToTripDB(Trip trip, Itinerary itinerary) {
        CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId().toString()).collection("itineraries");
        itineraryCollection.document(itinerary.getUniqueID().toString())
                .set(itinerary)
                .addOnSuccessListener(unused -> Log.d("Firestore", String.format("Itinerary added!", trip.getDestination())))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding itinerary", e));
    }

    /**
     * Fetches the itineraries from the DB
     * @param trips: trips whose itineraries we are fetching from DB
     */
    public void fetchItinerariesDB(TripManager trips) {
        for (Trip trip: trips.getTrips()) {
            ArrayList<Itinerary> itinerariesDB = new ArrayList<>();
            CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId()).collection("itineraries");
            itineraryCollection.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot itineraryDoc: queryDocumentSnapshots) {
                            Itinerary itinerary = itineraryDoc.toObject(Itinerary.class);
                            itinerariesDB.add(itinerary);
                            Log.d("Firebase", itinerary.getActivity() + " successfully fetched");
                        }
                        trip.setItineraries(itinerariesDB);
                    });
        }
    }

    /**
     * Deletes all itineraries of a trip in DB
     * @param trip: trip whose itineraries will all be deleted
     */
    public void deleteAllItinerariesDB(Trip trip) {
        CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId().toString()).collection("itineraries");
        itineraryCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    itineraryCollection.document(document.getId()).delete()
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Successfully deleted itinerary: " + document.getId()))
                            .addOnFailureListener(e -> Log.w("Firebase", "Error deleting itinerary", e));
                }
            }
        });
    }

    /**
     * Edits an existing itinerary in the DB
     * @param trip: trip whose itinerary is related to
     * @param itinerary: itinerary to be edited
     */
    public void editItineraryDB(Trip trip, Itinerary itinerary) {
        CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId().toString()).collection("itineraries");
        DocumentReference itineraryDoc = itineraryCollection.document(itinerary.getUniqueID());
        itineraryDoc.update("activity", itinerary.getActivity(),
                "cost", itinerary.getCost(),
                "location", itinerary.getLocation(),
                "date", itinerary.getDate(),
                "timeStart", itinerary.getTimeStart(),
                "timeEnd", itinerary.getTimeEnd(),
                "dateAndTime", itinerary.getDateAndTime())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Itinerary successfully updated!"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating itinerary", e));
    }

    /**
     * Deletes an itinerary from DB
     * @param trip: trip whose itinerary is related to
     * @param itinerary: to be deleted itinerary
     */
    public void deleteItineraryDB(Trip trip, Itinerary itinerary) {
        CollectionReference itineraryCollection = tripCollection.document(trip.getUniqueId().toString()).collection("itineraries");
        DocumentReference itineraryDoc = itineraryCollection.document(itinerary.getUniqueID());
        itineraryDoc.delete()
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Successfully deleted itinerary: "))
                .addOnFailureListener(e -> Log.w("Firebase", "Error deleting itinerary", e));

    }
}
