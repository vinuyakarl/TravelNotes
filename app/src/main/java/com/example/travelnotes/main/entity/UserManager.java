package com.example.travelnotes.main.entity;


import com.example.travelnotes.main.control.UserDB;

import java.util.ArrayList;

/**
 * This class stores all users of the app and uses a Singleton pattern. Basic setters and getters,
 * fetching users from DB, as well as getting users, checking if user exists, and adding a user is found
 * in this class.
 */
public class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    private static final UserManager instance = new UserManager();
    private final UserDB userDB = new UserDB();
    private User currentUser;


    private UserManager() {}

    public static UserManager getInstance() {
        return instance;
    }

    /**
     * Checks if a user exists in the userManager
     * @param username: username to be checked
     * @return boolean: if user exists or not
     */
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

    /**
     * Returns a User using its username
     * @param username: username used to find the User object
     * @return
     */
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

    public void fetchUsers() {
        userDB.fetchUsersDB();
    }
}
