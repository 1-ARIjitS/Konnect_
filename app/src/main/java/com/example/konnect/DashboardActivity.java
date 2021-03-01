package com.example.konnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    EditText meet;
    Button share, join, logout;
    String meetCode;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        meet = findViewById(R.id.codeBox);
        share = findViewById(R.id.shareButton);
        join = findViewById(R.id.joinButton);
        logout = findViewById(R.id.logout_button);
        meetCode = meet.getText().toString();
        auth = FirebaseAuth.getInstance();

        URL serverUrl;

        try {
            serverUrl = new URL("https://meet.jit.si");

            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverUrl)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (meetCode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Invlaid,Blank Field please enter a valid meeting code", Toast.LENGTH_SHORT).show();
                } else {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(meet.getText().toString())
                            .setWelcomePageEnabled(false)
                            .build();

                    JitsiMeetActivity.launch(DashboardActivity.this, options);
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (meetCode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Invlaid,Blank Field please enter a valid meeting code", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_TEXT, "Here is the code to join the meeting" + " " + meetCode);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "classified meeting code");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });
    }
}