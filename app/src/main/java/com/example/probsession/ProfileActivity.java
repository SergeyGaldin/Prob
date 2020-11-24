package com.example.probsession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.probsession.SharedPreferences.SaveToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ImageView logoutImg;
    TextView userName;

    EditText login, password;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        logoutImg = findViewById(R.id.imgOut);
        userName = findViewById(R.id.userName);
        mAuth = FirebaseAuth.getInstance();

        printUsername();
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

    public void outClick(View view) {
        SaveToken.save(this, "token", "false");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void renamePassword(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_password, null);
        builder.setView(view).setCancelable(false);
        AlertDialog dialog = builder.create();

        password = view.findViewById(R.id.password);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.rename).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String pas = password.getText().toString();
                user.updatePassword(pas).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "go", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    public void renameLogin(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_login, null);
        builder.setView(view).setCancelable(false);
        AlertDialog dialog = builder.create();

        login = view.findViewById(R.id.login);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.rename).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    public void historyClick(View view) {
        startActivity(new Intent(this, HistoryUserActivity.class));
    }

    public void infClick(View view) {
        startActivity(new Intent(this, InformationActivity.class));
    }

}