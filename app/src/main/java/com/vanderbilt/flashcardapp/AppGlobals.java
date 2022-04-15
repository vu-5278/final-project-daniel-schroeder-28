package com.vanderbilt.flashcardapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class AppGlobals extends Application {
    private static FirebaseUser USER;
    private static String LAST_SET_STUDIED = "";
    private static HashMap<String,ArrayList<HashMap<String,String>>> USER_SETS = new HashMap<>();

    public static HashMap<String,ArrayList<HashMap<String,String>>> getUserSets() {
        return USER_SETS;
    }

    public static void setUserSets(HashMap<String,ArrayList<HashMap<String,String>>> userSets) {
        USER_SETS = userSets;
    }

    public static void addToUserSets(String setName, ArrayList<HashMap<String,String>> set) {
        USER_SETS.put(setName, set);
    }

    public static void removeFromUserSets(String setName) {
        USER_SETS.remove(setName);
    }

    public static String getLastSetStudied() {
        return LAST_SET_STUDIED;
    }

    public static void setLastSetStudied(String lastSetStudied) {
        LAST_SET_STUDIED = lastSetStudied;
    }

    public static void setUser(FirebaseUser user) {
        USER = user;
    }

    public static FirebaseUser getUser() {
        return USER;
    }
}

