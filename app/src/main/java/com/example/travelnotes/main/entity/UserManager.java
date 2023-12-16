package com.example.travelnotes.main.entity;

import android.util.Log;

import com.example.travelnotes.main.control.UserDB;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    private static final UserManager instance = new UserManager();
    private final UserDB userDB = new UserDB();
    private User currentUser;


    private UserManager() {}

    public static UserManager getInstance() {
        return instance;
    }

    public boolean containsUser(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        if (!containsUser(user.getUsername())) {
            users.add(user);
            userDB.addUserDB(user);
        }
    }
    public User getUserByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return new User("egokogek", "giejgoej");
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets all users from DB and populate local userArrayList from DB
     */
    public void fetchUsers() {
        userDB.fetchUsersDB();
    }
}
