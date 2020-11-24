package com.example.probsession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.probsession.Adapters.Ad.HistoryAdapter.History;
import com.example.probsession.SharedPreferences.SaveToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Document document;
    TextView textUsd, textEur;
    String usd = "";
    String eur = "";
    EditText login;
    EditText password;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        TokenSave();
        TextView text_data = findViewById(R.id.textDate);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String data = dateFormat.format(new Date());
        text_data.setText(data);
    }

    private void init() {
        textUsd = findViewById(R.id.textUsd);
        textEur = findViewById(R.id.textEur);
        ImageButton butBranch = findViewById(R.id.butBranch);
        ImageButton butCourse = findViewById(R.id.butCourse);
        ImageButton butCome = findViewById(R.id.butCome);
        mAuth = FirebaseAuth.getInstance();

        butBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BranchesActivity.class));
            }
        });
        butCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CourseActivity.class));
            }
        });

        butCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.registation_form, null);
                builder.setView(view).setCancelable(false);
                AlertDialog alertDialog = builder.create();

                login = view.findViewById(R.id.login);
                password = view.findViewById(R.id.password);

                view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        authorization();
                    }
                });
                alertDialog.show();
            }
        });


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                parsCourse();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void authorization() {
        if (!TextUtils.isEmpty(login.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
            mAuth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                SaveToken.save(MainActivity.this,"token", "true");
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Акаунт не найден", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Проверь ввод", Toast.LENGTH_SHORT).show();
        }
    }



    private void parsCourse() {
        try {
            document = Jsoup.connect("https://www.cbr.ru/key-indicators/").get();
            Elements body = document.getElementsByTag("tbody");
            Element element = body.get(0);
            Elements elements = element.children();
            Element element1 = elements.get(1);
            Element element2 = elements.get(2);

            usd = element1.children().get(2).text().substring(0, 5);
            eur = element2.children().get(2).text().substring(0, 5);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textUsd.setText(usd);
                    textEur.setText(eur);
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void TokenSave() {
        Boolean check = Boolean.valueOf(SaveToken.read(MainActivity.this, "token", "true"));
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("token", check);
        if (check) {
            startActivity(intent);
        }
    }

}