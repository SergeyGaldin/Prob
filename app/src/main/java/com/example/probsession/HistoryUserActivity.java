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
import android.widget.TextView;

import com.example.probsession.Adapters.Ad.HistoryAdapter.History;
import com.example.probsession.Adapters.Ad.HistoryAdapter.HistoryAdapter;
import com.example.probsession.Adapters.Ad.ScoreAdapter.Score;
import com.example.probsession.Adapters.Ad.ScoreAdapter.ScoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryUserActivity extends AppCompatActivity {
    TextView userName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth;

    RecyclerView recyclerHistory;
    ArrayList<History> historyArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        init();
    }

    private void init() {
        userName = findViewById(R.id.userName);
        mAuth = FirebaseAuth.getInstance();
        recyclerHistory = findViewById(R.id.listHistory);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        setHistoryList();
        printUsername();

    }


    private void setHistoryList() {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            String date = "";
            String time = "";

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(mAuth.getUid())) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            if (s.getKey().equals("HistoryUser")) {
                                for (DataSnapshot snap : s.getChildren()) {
                                    for (DataSnapshot snapNum : snap.getChildren()) {
                                        if (snapNum.getKey().equals("date")) {
                                            date = snapNum.getValue().toString();
                                        }
                                        if (snapNum.getKey().equals("time")) {
                                            time = snapNum.getValue().toString();
                                            historyArrayList.add(new History(date, time));

                                        }
                                    }
                                }
                            }
                            HistoryAdapter historyAdapter = new HistoryAdapter(HistoryUserActivity.this, historyArrayList);

                            recyclerHistory.setAdapter(historyAdapter);
                        }
                    }
                }
                Collections.reverse(historyArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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