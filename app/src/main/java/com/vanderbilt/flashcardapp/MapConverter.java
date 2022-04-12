package com.vanderbilt.flashcardapp;

import java.util.ArrayList;
import java.util.HashMap;

public class MapConverter {

    public static HashMap<String, HashMap<String,String>> convertStringToMap(String inputString) {
        HashMap<String,HashMap<String,String>> fullMap = new HashMap<>();
        String[] splitFullString = inputString.substring(1,inputString.length()-2).split("\\},");

        ArrayList<String> fullMapKeySet = new ArrayList<>();
        ArrayList<String> fullMapNonMappedValues = new ArrayList<>();

        for (String s : splitFullString) {
            String[] splitPartialString = s.split("=\\{");
            for (String t : splitPartialString) {
                if (t.contains("=")) {
                    fullMapNonMappedValues.add(t);
                } else {
                    fullMapKeySet.add(t);
                }
            }

            for (int i = 0; i < fullMapNonMappedValues.size(); i++) {
                String[] splitAllValues = fullMapNonMappedValues.get(i).split(", ");
                HashMap<String,String> tempMap = new HashMap<>();

                for (String j : splitAllValues) {
                    String[] splitSingleValue = j.split("=");
                    tempMap.put(splitSingleValue[0].trim(),splitSingleValue[1].trim());
                }

                fullMap.put(fullMapKeySet.get(i).trim(), tempMap);
            }
        }
        return fullMap;
    }
}
