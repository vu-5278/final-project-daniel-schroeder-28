package com.vanderbilt.flashcardapp;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashcardAppTest {
    @Test
    public void mapConversionIsCorrect() {
        String mapRepresentation = "{test=[{testFront1=testBack1}, {testFront2=testBack2}, {testFront3=testBack3}], Food=[{Bread=Grain}, {Milk=Dairy}]}";
        HashMap<String, ArrayList<HashMap<String,String>>> testMap = MapConverter.convertStringToMap(mapRepresentation);

        assertEquals(2, testMap.size());
        assertTrue(testMap.containsKey("test") && testMap.containsKey("Food"));
        assertEquals(3, testMap.get("test").size());
        assertEquals(2, testMap.get("Food").size());

        for (int i = 0; i < testMap.get("test").size(); i++) {
            assertFalse(testMap.get("test").get(i).containsKey("") || testMap.get("test").get(i).containsValue(""));
        }

        for (int i = 0; i < testMap.get("Food").size(); i++) {
            assertFalse(testMap.get("Food").get(i).containsKey("") || testMap.get("Food").get(i).containsValue(""));
        }

        mapRepresentation = "";
        testMap = MapConverter.convertStringToMap(mapRepresentation);
        assertEquals(new HashMap<String,ArrayList<HashMap<String,String>>>(), testMap);
    }
}