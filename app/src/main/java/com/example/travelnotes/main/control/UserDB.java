package com.example.travelnotes.main.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class UserDB {
    private CollectionReference userCollection;
    private FirebaseFirestore db;
    private ArrayList<User> usersDB = new ArrayList<>();

    public UserDB() {
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");
    }

    public void addUserDB(User user) {
        userCollection.document(user.getUsername()).set(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d("LoginActivity", "Username successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("LoginActivity", "Error writing username", e);
                });
    }

    public Task<QuerySnapshot> retrieveUsersFromDB() {
        return userCollection.get();
    }



    public CollectionReference getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(CollectionReference userCollection) {
        this.userCollection = userCollection;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void fetchUsersDB() {
        ArrayList<User> usersDB = new ArrayList<>();
        userCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        User user = doc.toObject(User.class);
                        usersDB.add(user);
                        Log.d("Firestore", user.getUsername());
                    }
                });
    }

    public ArrayList<User> getUsersDB() {
        return usersDB;
    }
}
