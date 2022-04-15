package com.vanderbilt.flashcardapp;

import java.util.ArrayList;
import java.util.HashMap;

public class MapConverter {

    /**
     * converts a string representation of a map into an actual map
     * Useful for db queries as they come back as strings but maps are easier to manipulate
     * @param inputString - The string to convert, must be in format {real=[{fsda=sdf }, {qer=qwer}, {qwrqw=qrwq}], rea=[{fsda=sdf }, {qer=qwer}, {qwrqw=qrwq}]}
     *                    basically a list of maps within a map
     * @return - The map of the string representation
     */

    public static HashMap<String, ArrayList<HashMap<String,String>>> convertStringToMap(String inputString) {
        if (inputString.length() == 0) {
            return new HashMap<>();
        }

        HashMap<String,ArrayList<HashMap<String,String>>> fullMap = new HashMap<>();
        String[] splitFullString = inputString.substring(1,inputString.length()-3).split("\\}\\], ");

        ArrayList<String> fullMapKeySet = new ArrayList<>();
        ArrayList<String> fullMapNonMappedValues = new ArrayList<>();

        for (String s : splitFullString) {
            String[] splitPartialString = s.split("=\\[\\{");
            for (String t : splitPartialString) {
                if (t.contains("=")) {
                    fullMapNonMappedValues.add(t);
                } else {
                    fullMapKeySet.add(t);
                }
            }


            for (int i = 0; i < fullMapNonMappedValues.size(); i++) {
                String[] splitAllValues = fullMapNonMappedValues.get(i).split("\\}, \\{");
                ArrayList<HashMap<String,String>> tempList = new ArrayList<>();

                for (String j : splitAllValues) {
                    HashMap<String,String> tempMap = new HashMap<>();
                    String[] splitSingleValue = j.split("=");
                    tempMap.put(splitSingleValue[0].trim(),splitSingleValue[1].trim());
                    tempList.add(tempMap);
                }


                fullMap.put(fullMapKeySet.get(i).trim(), new ArrayList<>(tempList));
            }
        }

        return fullMap;
    }
}
