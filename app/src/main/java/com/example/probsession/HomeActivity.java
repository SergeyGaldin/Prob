package com.example.probsession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.probsession.Adapters.Ad.CardAdapter.Card;
import com.example.probsession.Adapters.Ad.CardAdapter.CardAdapter;
import com.example.probsession.Adapters.Ad.HistoryAdapter.History;
import com.example.probsession.Adapters.Ad.ScoreAdapter.Score;
import com.example.probsession.Adapters.Ad.ScoreAdapter.ScoreAdapter;
import com.example.probsession.Constructors.DateConstructor;
import com.example.probsession.SharedPreferences.SaveToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Integer.parseInt;


public class HomeActivity extends AppCompatActivity {
    ImageView logout;
    TextView userName;
    ProgressBar progressBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth;

    RecyclerView recyclerCard;
    RecyclerView recyclerScore;

    ArrayList<Card> cardArrayList = new ArrayList<>();
    ArrayList<Score> scoreArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        data();
    }

    private void init() {
        logout = findViewById(R.id.imgOut);
        userName = findViewById(R.id.userName);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        recyclerCard = findViewById(R.id.listCard);

        recyclerCard.setLayoutManager(new LinearLayoutManager(this));
        recyclerCard.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerScore = findViewById(R.id.listSchet);
        recyclerScore.setLayoutManager(new LinearLayoutManager(this));
        recyclerScore.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        printUsername();
        listCard();
        scoreCard();
    }

    private void data() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query lastQuery = databaseReference.child(mAuth.getUid()).child("HistoryUser").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String date = dateFormat.format(new Date());
                DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
                String time = dateFormat1.format(new Date());

                if (dataSnapshot.getKey().equals("HistoryUser")) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        int message = Integer.parseInt(snap.getKey());
                        Log.e("My", "" + message);
                        for (int i = message; ; ) {
                            i++;

                            FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid()).child("HistoryUser").child(String.valueOf(i)).child("date").setValue(date);
                            FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid()).child("HistoryUser").child(String.valueOf(i)).child("time").setValue(time);

                            break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });
    }


    private void scoreCard() {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String numS = "";
                String moneyS = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    scoreArrayList.clear();
                    if (dataSnapshot.getKey().equals(mAuth.getUid())) {
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            if (s.getKey().equals("Score")) {
                                for (DataSnapshot score : s.getChildren()) {
                                    for (DataSnapshot num : score.getChildren()) {
                                        if (num.getKey().equals("num")) {
                                            numS = num.getValue().toString();
                                        }
                                    }
                                    for (DataSnapshot money : score.getChildren()) {
                                        if (money.getKey().equals("money")) {
                                            moneyS = money.getValue().toString();
                                            scoreArrayList.add(new Score(numS, moneyS));
                                            ScoreAdapter scoreAdapter = new ScoreAdapter(HomeActivity.this, scoreArrayList);
                                            recyclerScore.setAdapter(scoreAdapter);

                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void listCard() {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            CardAdapter cardAdapter;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String numC = "";
                String moneyC = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cardArrayList.clear();
                    if (snapshot.getKey().equals(mAuth.getUid())) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            for (int i = 0; i <= s.getKey().length(); i++) {
                                Log.e("My", "nuq" + s);
                                if (s.getKey().equals("Card")) {
                                    for (DataSnapshot card : s.getChildren()) {
                                        for (DataSnapshot num : card.getChildren()) {
                                            if (num.getKey().equals("num")) {
                                                numC = num.getValue().toString();
                                                Log.e("My", "money" + numC);
                                            }
                                        }
                                        for (DataSnapshot money : card.getChildren()) {
                                            if (money.getKey().equals("money")) {
                                                moneyC = money.getValue().toString();
                                                Log.e("My", "money" + moneyC);
                                                cardArrayList.add(new Card(numC, moneyC));
                                                cardAdapter = new CardAdapter(HomeActivity.this, cardArrayList);
                                                recyclerCard.setAdapter(cardAdapter);

                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                break;
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

    public void logoutClick(View view) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}