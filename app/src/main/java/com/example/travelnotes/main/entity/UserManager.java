package com.example.travelnotes.main.entity;

import android.util.Log;

import com.example.travelnotes.main.control.UserDB;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    private static UserManager instance = new UserManager();
    private UserDB userDB = new UserDB();


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

    public ArrayList<User> getUsers() {
        return users;
    }


    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void fetchUsers() {
        userDB.fetchUsersDB();
        ArrayList<User> usersInDB = userDB.getUsersDB();
        setUsers(usersInDB);
    }
}
