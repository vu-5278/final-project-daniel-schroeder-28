package com.vanderbilt.flashcardapp;

import java.util.ArrayList;
import java.util.HashMap;

public class MapConverter {

    /**
     * converts a string representation of a map into an actual map
     * Useful for db queries as they come back as strings but maps are easier to manipulate
     * @param inputString - The string to convert, must be in format {Title={Test=Test}} OR {Title={Test2=Test2}, Title2={Test3=Test3,Test4=Test4}} etc
     *                    basically a map withing a map
     * @return - The map of the string representation
     */
    public static HashMap<String, HashMap<String,String>> convertStringToMap(String inputString) {
        if (inputString.length() == 0) {
            return new HashMap<>();
        }

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
