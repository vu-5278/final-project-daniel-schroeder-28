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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class MySetsActivity extends AppCompatActivity {


    public static RecyclerView recyclerView;
    public FirebaseUser user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sets);

        user = AppGlobals.getUser();

        recyclerView = findViewById(R.id.recyclerViewUserSets);
        if (user != null) {
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        AppGlobals.setUserSets(MapConverter.convertStringToMap(document.getData().get("sets").toString()));
                        if (AppGlobals.getUserSets()!= null && AppGlobals.getUserSets().size() > 0) {
                            FlashcardSetsAdapter adapter = new FlashcardSetsAdapter(new ArrayList<>(AppGlobals.getUserSets().keySet()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        }
                    } else {
                        AppGlobals.setUserSets(null);
                    }
                } else {
                    AppGlobals.setUserSets(null);
                }
            });
        } else {
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
                startActivity(intentStudy);
                break;
            case (R.id.add):
                Intent intentAddSet = new Intent(this, AddSetActivity.class);
                startActivity(intentAddSet);
                break;
        }
        return true;
    }
}