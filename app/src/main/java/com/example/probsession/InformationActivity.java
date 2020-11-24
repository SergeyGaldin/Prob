package com.example.probsession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationActivity extends AppCompatActivity {
    TextView userName;
    TextView deal;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        init();
    }

    private void init() {
        userName = findViewById(R.id.userName);
        deal = findViewById(R.id.deal);
        mAuth = FirebaseAuth.getInstance();

        printUsername();
        clickWeb();
    }

    private void clickWeb() {
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.avangard.ru/rus/docs/rules_cart_use_in_system_mobile_pays.pdf"));
                startActivity(browserIntent);
            }
        });
    }

    public void backClick(View view) {
        onBackPressed();
    }

    private void printUsername() {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(mAuth.getUid())) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            if (s.getKey().equals("name")) {
                                String name = s.getValue().toString();
                                userName.setText(name);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}