package com.vanderbilt.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class NewUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.buttonCreateAccount);
        button.setOnClickListener(this::createAccount);

        TextView textView = findViewById(R.id.textViewAlreadyHaveAccount);
        textView.setOnClickListener((this::alreadyHaveAccount));
    }

    /**
     * function to go back to login page
     * @param v - View
     */
    public void alreadyHaveAccount(View v) {
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivity(intent);
    }

    /**
     * function to create account
     * @param v - View
     */
    public void createAccount(View v) {
        findViewById(R.id.textViewRandomError).setAlpha(0);
        String pass = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        String reenterPass = ((EditText) findViewById(R.id.editTextReenterPassword)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        if (email.length() == 0 || pass.length() == 0 || reenterPass.length() == 0) {
            ((TextView) findViewById(R.id.textViewRandomError)).setText("Please enter all fields");
            findViewById(R.id.textViewRandomError).setAlpha(1);
            return;
        } else if (!pass.equals(reenterPass)) {
            ((TextView) findViewById(R.id.textViewRandomError)).setText("Passwords do not match");
            findViewById(R.id.textViewRandomError).setAlpha(1);
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference docRef = db.collection("users").document(user.getUid());
                            docRef.get().addOnCompleteListener(newDocTask -> {
                                if (newDocTask.isSuccessful()) {
                                    HashMap<String, String> fieldsMap = new HashMap<>();
                                    fieldsMap.put("uid", user.getUid());
                                    docRef.set(fieldsMap).addOnSuccessListener(documentReference -> {
                                        Intent intent = new Intent(NewUserActivity.this, MySetsActivity.class);
                                        intent.putExtra("user", user);
                                        startActivity(intent);
                                    });
                                }
                            });
                        } else {
                            ((TextView) findViewById(R.id.textViewRandomError)).setText(task.getException().getMessage());
                            findViewById(R.id.textViewRandomError).setAlpha(1);
                        }
                    });
        }
    }
}
