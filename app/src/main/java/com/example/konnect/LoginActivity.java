package com.example.konnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button signUpButton;
    Button loginButton;
    EditText email;
    EditText password;

    FirebaseAuth auth;
    String emailBox, pass;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.newAccount);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailBox = email.getText().toString();
                pass = password.getText().toString();
                if (emailBox.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Invalid,Blank Field", Toast.LENGTH_SHORT).show();
                } else if (emailBox.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Invalid,please enter an Email Id", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Invalid,please enter a Password", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6) {
                    Toast.makeText(LoginActivity.this, "password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    auth.signInWithEmailAndPassword(emailBox, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }
}