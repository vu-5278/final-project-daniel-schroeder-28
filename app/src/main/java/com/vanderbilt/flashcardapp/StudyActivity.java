package com.vanderbilt.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyActivity extends AppCompatActivity {

    public FirebaseUser user;
    boolean front;
    public static ArrayList<HashMap<String,String>> flashcardList;
    public static int index;
    Button buttonNextCard;
    Button buttonPreviousCard;
    TextView currentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        buttonNextCard = findViewById(R.id.buttonNextCard);
        buttonPreviousCard = findViewById(R.id.buttonPreviousCard);
        currentCard = findViewById(R.id.textViewCurrentCard);

        if (AppGlobals.getLastSetStudied().equals("")) {
            buttonNextCard.setAlpha(0);
            buttonPreviousCard.setAlpha(0);
            currentCard.setAlpha(0);
            ((TextView) findViewById(R.id.textStudyInstructions)).setText("Please select a set from the \"My Sets\" page to start");
            (findViewById(R.id.textStudyInstructions)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            loadSet(AppGlobals.getLastSetStudied());
        }

        user = AppGlobals.getUser();
        front = true;

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.study);
        navBar.setOnItemSelectedListener(this::navigate);

        buttonNextCard.setOnClickListener(this::nextStudyCard);
        buttonPreviousCard.setOnClickListener(this::previousStudyCard);
        currentCard.setOnClickListener(this::flipCard);
    }

    private void loadSet(String lastSetStudied) {
        flashcardList = AppGlobals.getUserSets().get(lastSetStudied);
        index = 0;
        String frontText = "";
        for (String k : flashcardList.get(index).keySet()) {
            frontText = k;
        }
        currentCard.setText(frontText);
    }

    public void previousStudyCard(View view) {
        if (index == 0) {
            return;
        }
        front = true;
        index--;
        String frontText = "";
        for (String k : flashcardList.get(index).keySet()) {
            frontText = k;
        }

        currentCard.setText(frontText);
    }

    public void nextStudyCard(View view) {
        if (flashcardList.size() <= index + 1) {
            return;
        }
        front = true;
        index++;
        String frontText = "";
        for (String k : flashcardList.get(index).keySet()) {
            frontText = k;
        }

        currentCard.setText(frontText);
    }

    public void flipCard(View view) {
        String frontText = "";
        for (String k : flashcardList.get(index).keySet()) {
            frontText = k;
        }

        if (front) {
            currentCard.setText(flashcardList.get(index).get(frontText));
            front = false;
        } else {
            currentCard.setText(frontText);
            front = true;
        }
    }

    /**
     * Function to navigate between pages
     * @param menuItem - menuItem clicked on
     * @return - true or false
     */
    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.sets):
                Intent intentMySets = new Intent(this, MySetsActivity.class);
                startActivity(intentMySets);
                break;
            case (R.id.study):
                return false;
            case (R.id.add):
                Intent intentAddSet = new Intent(this, AddSetActivity.class);
                startActivity(intentAddSet);
                break;
        }
        return true;
    }
}