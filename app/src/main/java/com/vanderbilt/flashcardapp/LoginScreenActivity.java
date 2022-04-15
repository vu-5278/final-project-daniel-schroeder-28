package com.vanderbilt.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button button = findViewById(R.id.buttonSignIn);
        button.setOnClickListener(this::login);

        TextView textView = findViewById(R.id.newUserTextView);
        textView.setOnClickListener((this::newUser));

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * function to log into the app
     * @param v - View
     */
    public void login(View v) {
        findViewById(R.id.textViewRandomError).setAlpha(0);
        String pass = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        if (email.length() > 0 && pass.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginScreenActivity.this, MySetsActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            } else {
                                ((TextView) findViewById(R.id.textViewRandomError)).setText(task.getException().getMessage());
                                findViewById(R.id.textViewRandomError).setAlpha(1);
                            }
                        }
                    });
        } else {
            ((TextView) findViewById(R.id.textViewRandomError)).setText("Please enter all fields");
            findViewById(R.id.textViewRandomError).setAlpha(1);
        }
    }

    /**
     * function to navigate to account creation page
     * @param v - View
     */
    public void newUser(View v) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }
}