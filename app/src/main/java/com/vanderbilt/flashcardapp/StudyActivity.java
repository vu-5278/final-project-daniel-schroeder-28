package com.vanderbilt.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;

public class StudyActivity extends AppCompatActivity {

    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        user = AppGlobals.getUser();

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.study);
        navBar.setOnItemSelectedListener(this::navigate);
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
                intentMySets.putExtra("user", user);
                startActivity(intentMySets);
                break;
            case (R.id.study):
                return false;
            case (R.id.add):
                Intent intentAddSet = new Intent(this, AddSetActivity.class);
                intentAddSet.putExtra("user", user);
                startActivity(intentAddSet);
                break;
        }
        return true;
    }
}