package com.example.probsession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.probsession.Adapters.Ad.CardAdapter.Card;
import com.example.probsession.Adapters.Ad.CardAdapter.CardAdapter;
import com.example.probsession.Adapters.Ad.CardProfileAdapter.CardProfile;
import com.example.probsession.Adapters.Ad.CardProfileAdapter.CardProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {
    TextView userName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth;

    RecyclerView recyclerListCardProfile;
    ArrayList<CardProfile> profileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        init();
    }

    private void init() {
        userName = findViewById(R.id.userName);
        recyclerListCardProfile = findViewById(R.id.listProfileCard);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerListCardProfile.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();

        printUsername();
        listCardProfile();
    }

    private void listCardProfile() {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String numC = "";
                String moneyC = "";
                String block = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    profileArrayList.clear();
                    if (snapshot.getKey().equals(mAuth.getUid())) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            if (s.getKey().equals("Card")) {
                                for (DataSnapshot card : s.getChildren()) {
                                    for (DataSnapshot num : card.getChildren()) {
                                        if (num.getKey().equals("num")) {
                                            numC = num.getValue().toString();
                                            Log.e("My", "money" + numC);
                                        }
                                    }
                                    for (DataSnapshot ban : card.getChildren()){
                                        if (ban.getKey().equals("active")){
                                            block = ban.getValue().toString();
                                            if (block.equals("true")){
                                                block = "";
                                            } else {
                                                block = "Карта заблокирована";
                                                LayoutInflater inflater = getLayoutInflater();
                                                View v = inflater.inflate(R.layout.card_profile_item, (RelativeLayout) findViewById(R.id.RelLoq));
                                                ImageButton gr = v.findViewById(R.id.renLoq);
                                                gr.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                    for (DataSnapshot money : card.getChildren()) {
                                        if (money.getKey().equals("money")) {
                                            moneyC = money.getValue().toString();
                                            Log.e("My", "money" + moneyC);
                                            profileArrayList.add(new CardProfile(numC, moneyC, block));
                                            CardProfileAdapter cardAdapter = new CardProfileAdapter(CardActivity.this, profileArrayList);
                                            recyclerListCardProfile.setAdapter(cardAdapter);
                                        }
                                    }
                                }
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

    public void backClick(View view) {
        onBackPressed();
    }

    public void blockCard(View view) {


    }
}