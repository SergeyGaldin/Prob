package com.example.probsession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.probsession.Adapters.Ad.BranchesAdapter.Branch;
import com.example.probsession.Adapters.Ad.BranchesAdapter.BranchesAdapter;

import java.util.ArrayList;

public class BranchesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Branch> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        setAdapter();
    }

    private void setAdapter() {
        recyclerView = findViewById(R.id.listBranch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        setElements();
        BranchesAdapter branchesAdapter = new BranchesAdapter(this, arrayList);
        recyclerView.setAdapter(branchesAdapter);
    }

    private void setElements() {
        String address = "Москва, ул.Вавилова, д.7";
        String placeBr = "Банкомат";
        String workA = "Работает";
        String clockA = "Часы работы 00:00-00:00";

        String placeOt = "Отделение";
        String workB = "Не работает";
        String clockB = "Часы работы 09:00-20:00";

        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeOt, workB, clockB));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeOt, workB, clockB));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
        arrayList.add(new Branch(address, placeOt, workB, clockB));
        arrayList.add(new Branch(address, placeBr, workA, clockA));
    }
}