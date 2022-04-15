package com.vanderbilt.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MySetsActivity extends AppCompatActivity {


    public static RecyclerView recyclerView;
    public FirebaseUser user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sets);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        recyclerView = findViewById(R.id.recyclerViewUserSets);
        if (AppGlobals.getUserSets().size() > 0) {
            FlashcardSetsAdapter adapter = new FlashcardSetsAdapter(new ArrayList<>(AppGlobals.getUserSets().keySet()));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.sets);
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
                return false;
            case (R.id.study):
                Intent intentStudy = new Intent(this, StudyActivity.class);
                intentStudy.putExtra("user", user);
                startActivity(intentStudy);
                break;
            case (R.id.add):
                Intent intentAddSet = new Intent(this, AddSetActivity.class);
                intentAddSet.putExtra("user", user);
                startActivity(intentAddSet);
                break;
        }
        return true;
    }
}