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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {


    Button signUpButton;
    Button loginButton;
    EditText email;
    EditText password;
    EditText nameBox;

    FirebaseAuth auth;
    String emailBox, pass, name;
    FirebaseFirestore database;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.newAccount);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        nameBox = findViewById(R.id.username);
        auth = FirebaseAuth.getInstance();

        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait While We Are Creating Your Account...");


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailBox = email.getText().toString();
                pass = password.getText().toString();
                name = nameBox.getText().toString();

                final users user = new users();
                user.setEmail(emailBox);
                user.setPass(pass);
                user.setName(name);

                if (emailBox.isEmpty() && pass.isEmpty() && name.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid,Blank Field", Toast.LENGTH_SHORT).show();
                } else if (emailBox.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid,please enter an Email Id", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid,please enter a Password", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid,please enter an Username", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    auth.createUserWithEmailAndPassword(emailBox, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                database.collection("Users")
                                        .document().set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                            }
                                        });
                                Toast.makeText(SignUpActivity.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}