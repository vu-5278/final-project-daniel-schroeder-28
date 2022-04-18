package com.vanderbilt.flashcardapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSetActivity extends AppCompatActivity {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<HashMap<String,String>> flashcardList;
    public static int index;
    public EditText editTextFrontOfCard;
    public EditText editTextBackOfCard;
    public EditText editTextSetName;
    public boolean editingExistingSet;
    public String existingSetName;
    boolean saved;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set);

        user = AppGlobals.getUser();
        Intent intent = getIntent();
        editingExistingSet = intent.getBooleanExtra("editingExistingSet", false);
        existingSetName = intent.getStringExtra("existingSetName");

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.add);
        navBar.setOnItemSelectedListener(this::navigate);

        Button buttonNextCard = findViewById(R.id.buttonNextCard);
        buttonNextCard.setOnClickListener(this::nextCard);

        Button buttonPreviousCard = findViewById(R.id.buttonPreviousCard);
        buttonPreviousCard.setOnClickListener(this::previousCard);

        Button buttonSaveSet = findViewById(R.id.buttonSaveSet);
        buttonSaveSet.setOnClickListener(this::save);

        Button buttonNewSet = findViewById(R.id.buttonNewSet);
        buttonNewSet.setOnClickListener(this::newSet);

        editTextFrontOfCard = findViewById(R.id.editTextFrontOfCard);
        editTextFrontOfCard.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String key = "";
                for (String k : flashcardList.get(index).keySet()) {
                    key = k;
                }
                if (!editTextFrontOfCard.getText().toString().equals(key)) {
                    saved = false;
                }
            }
        });
        editTextBackOfCard = findViewById(R.id.editTextBackOfCard);
        editTextBackOfCard.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String key = "";
                for (String k : flashcardList.get(index).keySet()) {
                    key = k;
                }
                if (!editTextBackOfCard.getText().toString().equals(key)) {
                    saved = false;
                }
            }
        });
        editTextSetName = findViewById(R.id.editTextSetName);

        saved = true;

        if (editingExistingSet) {
            openSet(existingSetName);
        } else {
            index = 0;
            flashcardList = new ArrayList<>();
            HashMap<String,String> starterMap = new HashMap<>();
            starterMap.put("","");
            flashcardList.add(starterMap);
        }
    }

    /**
     * Function to navigate between pages
     * @param menuItem - menuItem clicked on
     * @return - true or false
     */
    private boolean navigate(MenuItem menuItem) {
        if (!saved) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please save before leaving page");
            builder.setTitle("Wait!");
            builder.setCancelable(false);

            builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            switch (menuItem.getItemId()) {
                case (R.id.sets):
                    Intent intentMySets = new Intent(this, MySetsActivity.class);
                    startActivity(intentMySets);
                    break;
                case (R.id.study):
                    Intent intentStudy = new Intent(this, StudyActivity.class);
                    startActivity(intentStudy);
                    break;
                case (R.id.add):
                    return false;
            }
            return true;
        }
        return false;
    }

    public void nextCard(View v) {
        HashMap<String,String> tempMap = new HashMap<>();
        tempMap.put(editTextFrontOfCard.getText().toString(),editTextBackOfCard.getText().toString());
        flashcardList.set(index, tempMap);
        index++;
        if (flashcardList.size() < index + 1) {
            flashcardList.add(new HashMap<String, String>() {{
                put("", "");
            }});
            editTextFrontOfCard.setText("");
            editTextBackOfCard.setText("");
        } else {
            String frontText = "";
            for (String k : flashcardList.get(index).keySet()) {
                frontText = k;
            }
            editTextFrontOfCard.setText(frontText);
            editTextBackOfCard.setText(flashcardList.get(index).get(frontText));
        }
    }

    public void previousCard(View v) {
        HashMap<String,String> tempMap = new HashMap<>();
        tempMap.put(editTextFrontOfCard.getText().toString(),editTextBackOfCard.getText().toString());
        flashcardList.set(index, tempMap);
        if (index == 0) {
            return;
        }
        index--;
        String frontText = "";
        for (String k : flashcardList.get(index).keySet()) {
            frontText = k;
        }
        editTextFrontOfCard.setText(frontText);
        editTextBackOfCard.setText(flashcardList.get(index).get(frontText));
    }

    public void newSet(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear the current set you are creating? Please ensure you have saved.");
        builder.setTitle("Wait!");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            index = 0;
            flashcardList = new ArrayList<>();
            flashcardList.add(new HashMap<String, String>() {{
                put("", "");
            }});
            editTextSetName.setText("");
            editTextFrontOfCard.setText("");
            editTextBackOfCard.setText("");
            editingExistingSet =  false;
            existingSetName = "";
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openSet(String unfinishedSetName) {
        index = 0;
        flashcardList = new ArrayList<>();
        editingExistingSet = true;
        existingSetName = unfinishedSetName;

        flashcardList = AppGlobals.getUserSets().get(unfinishedSetName);

        for (HashMap<String,String> hm : flashcardList) {
            for (String key : hm.keySet()) {
                if (key.equals("")) {
                    flashcardList.remove(hm);
                }
            }
        }
        String front = "";
        for (String key : flashcardList.get(index).keySet()) {
            front = key;
        }

        editTextSetName.setText(unfinishedSetName);
        editTextFrontOfCard.setText(front);
        editTextBackOfCard.setText(flashcardList.get(index).get(front));
    }

    public void save(View v) {
        boolean removeExistingFromDb = false;
        boolean badData = false;
        HashMap<String,String> tempMap = new HashMap<>();
        tempMap.put(editTextFrontOfCard.getText().toString(),editTextBackOfCard.getText().toString());
        flashcardList.set(index, tempMap);


        for (int i = 0; i < flashcardList.size(); i++) {
            if (flashcardList.get(i).containsKey("") && flashcardList.get(i).containsValue("") && flashcardList.size() == 1) {
                saved = true;
                return;
            } else if (flashcardList.get(i).containsKey("") && flashcardList.get(i).containsValue("") && flashcardList.size() > 1) {
                flashcardList.remove(i);
                i--;
            } else if (flashcardList.get(i).containsKey("") || flashcardList.get(i).containsValue("")) {
                badData = true;
            }
        }

        if (badData) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Set contains one or more blank fronts and/or backs. Please fix before saving");
            builder.setTitle("Error");
            builder.setCancelable(false);

            builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            String setName = editTextSetName.getText().toString();
            if (setName.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Set is missing a name, please enter one to continue");
                builder.setTitle("Error");
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return;
            } else if (editingExistingSet && AppGlobals.getUserSets().containsKey(setName)) {
                AppGlobals.addToUserSets(setName,flashcardList);
            } else if (!editingExistingSet && AppGlobals.getUserSets().containsKey(setName)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("If you save, this set will be combined with another with the same name. Are you sure you want to continue?");
                builder.setTitle("Wait!");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    ArrayList<HashMap<String,String>> tempFlashcardList = (ArrayList<HashMap<String, String>>) flashcardList.clone();
                    openSet(setName);
                    flashcardList.addAll(tempFlashcardList);
                    AppGlobals.addToUserSets(setName,flashcardList);
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (editingExistingSet && !existingSetName.equals(setName)) {
                removeExistingFromDb = true;
                AppGlobals.addToUserSets(setName,flashcardList);
                AppGlobals.removeFromUserSets(existingSetName);
            } else {
                AppGlobals.addToUserSets(setName,flashcardList);
            }

            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> fieldsMap = new HashMap<>();
                        fieldsMap.put("sets", AppGlobals.getUserSets());
                        docRef.set(fieldsMap, SetOptions.merge());
                    }
                }
            });

            if (removeExistingFromDb) {
                Map<String, Object> deleteFromDb = new HashMap<>();
                deleteFromDb.put("sets." + existingSetName, FieldValue.delete());
                docRef.update(deleteFromDb);
            }

            saved = true;
        }
    }
}