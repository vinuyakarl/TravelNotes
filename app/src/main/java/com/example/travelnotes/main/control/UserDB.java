package com.example.travelnotes.main.control;

import android.util.Log;

import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class UserDB {
    private final CollectionReference userCollection;
    private final FirebaseFirestore db;

    public UserDB() {
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");
    }

    public void addUserDB(User user) {
        userCollection.document(user.getUsername()).set(user)
                .addOnSuccessListener(aVoid -> Log.d("LoginActivity", "Username successfully written!"))
                .addOnFailureListener(e -> Log.w("LoginActivity", "Error writing username", e));
    }

    public void fetchUsersDB() {
        UserManager userManager = UserManager.getInstance();
        ArrayList<User> usersDB = new ArrayList<>();
        userCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        User user = doc.toObject(User.class);
                        usersDB.add(user);
                        Log.d("Firestore", user.getUsername());
                    }
                });
        userManager.setUsers(usersDB);
    }
}
