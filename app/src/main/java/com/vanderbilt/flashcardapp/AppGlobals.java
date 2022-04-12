package com.vanderbilt.flashcardapp;

import android.app.Application;

import java.util.HashMap;

public class AppGlobals extends Application {
    private static String USERNAME = "";
    private static String LAST_SET = "";
    private static HashMap<String,HashMap<String,String>> USER_SETS = new HashMap<>();

    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String username) {
        USERNAME = username;
    }

    public static String getLastSet() {
        return LAST_SET;
    }

    public static void setLastSet(String lastSet) {
        LAST_SET = lastSet;
    }

    public static HashMap<String,HashMap<String,String>> getUserSets() {
        return USER_SETS;
    }

    public static void setUserSets(HashMap<String,HashMap<String,String>> userSets) {
        USER_SETS = userSets;
    }

    public static void addToUserSets(String setName, HashMap<String,String> set) {
        USER_SETS.put(setName, set);
    }

    public static void removeFromUserSets(String setName) {
        USER_SETS.remove(setName);
    }
}

