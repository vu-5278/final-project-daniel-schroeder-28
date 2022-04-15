package com.vanderbilt.flashcardapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class AppGlobals extends Application {
    private static HashMap<String, ArrayList<HashMap<String,String>>> UNFINISHED_SET;
    private static String LAST_SET_STUDIED = "";
    private static HashMap<String,HashMap<String,String>> USER_SETS = new HashMap<>();

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

    public static HashMap<String, ArrayList<HashMap<String,String>>> getUnfinishedSet() {
        return UNFINISHED_SET;
    }

    public static void setUnfinishedSet(HashMap<String, ArrayList<HashMap<String,String>>> unfinishedSet) {
        UNFINISHED_SET = unfinishedSet;
    }

    public static String getLastSetStudied() {
        return LAST_SET_STUDIED;
    }

    public static void setLastSetStudied(String lastSetStudied) {
        LAST_SET_STUDIED = lastSetStudied;
    }

    public static HashMap<String, ArrayList<HashMap<String,String>>> getUnfinishedSetFromSetName(String setName) {
        HashMap<String, ArrayList<HashMap<String,String>>> returnMap = new HashMap<>();
        HashMap<String, String> flashcardSet = getUserSets().get(setName);

        ArrayList<String> keys = new ArrayList<>(flashcardSet.keySet());
        ArrayList<String> values = new ArrayList<>(flashcardSet.values());
        ArrayList<HashMap<String,String>> finalList = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            HashMap<String,String> tempMap = new HashMap<>();
            tempMap.put(keys.get(i),values.get(i));
            finalList.add(tempMap);
        }

        returnMap.put(setName, finalList);
        return returnMap;
    }
}

