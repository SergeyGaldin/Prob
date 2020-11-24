package com.example.probsession.Adapters.Ad.CardProfileAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probsession.CardActivity;
import com.example.probsession.MainActivity;
import com.example.probsession.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CardProfileAdapter extends RecyclerView.Adapter<CardProfileAdapter.CardProfileAdapterVH> {

    Context context;
    LayoutInflater inflater;
    List<CardProfile> profileList;

    public CardProfileAdapter(Context context, List<CardProfile> profileList) {
        this.inflater = inflater.from(context);
        this.context = context;
        this.profileList = profileList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CardProfileAdapter.CardProfileAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardProfileAdapterVH(inflater.inflate(R.layout.card_profile_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull CardProfileAdapter.CardProfileAdapterVH holder, int position) {
        CardProfile cardProfile = profileList.get(position);

        holder.num_card_prof.setText(cardProfile.getNum_card());
        holder.money_card_prof.setText(cardProfile.getMoney_card());
        holder.text_block_card.setText(cardProfile.getBlock());
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class CardProfileAdapterVH extends RecyclerView.ViewHolder {
        TextView num_card_prof, money_card_prof, text_block_card;
        ImageButton block;
        RelativeLayout rel;

        public CardProfileAdapterVH(@NonNull View itemView) {
            super(itemView);
            num_card_prof = itemView.findViewById(R.id.num_cards);
            money_card_prof = itemView.findViewById(R.id.money_cards);
            text_block_card = itemView.findViewById(R.id.text_block_card);
            block = itemView.findViewById(R.id.renLog);
            rel = itemView.findViewById(R.id.RelLoq);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            myRef.child("User").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String act;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().equals(mAuth.getUid())) {
                            for (DataSnapshot s : snapshot.getChildren()) {
                                if (s.getKey().equals("Card")) {
                                    for (DataSnapshot card : s.getChildren()) {
                                        for (DataSnapshot active : card.getChildren()) {
                                            Log.e("My", "QWE" + card);
                                            if (active.getKey().equals("active")) {
                                                act = active.getValue().toString();
                                                if (act.equals("false")) {
                                                    int positionIndex = getAdapterPosition();
                                                    return;
                                                }
                                            }
                                            break;
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

            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
                    View inflater = LayoutInflater.from(context).inflate(R.layout.rename_password, null);
                    builder.setView(inflater).setCancelable(false);
                    AlertDialog alertDialog = builder.create();

                    TextView title = inflater.findViewById(R.id.TitleAl);
                    title.setText("Блокировка карты");
                    TextView vod = inflater.findViewById(R.id.textVod);
                    vod.setText("Вы уверены, что хотите заблокировать карту?");
                    EditText password = inflater.findViewById(R.id.password);
                    password.setHint("Пароль");

                    inflater.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    Button success = inflater.findViewById(R.id.rename);
                    success.setText("Заблокировать");
                    success.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText password = inflater.findViewById(R.id.password);
                            if (!TextUtils.isEmpty(password.getText().toString())) {
                                mAuth.signInWithEmailAndPassword(mAuth.getUid(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        myRef.child("User").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String act;
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    if (snapshot.getKey().equals(mAuth.getUid())) {
                                                        for (DataSnapshot s : snapshot.getChildren()) {
                                                            if (s.getKey().equals("Card")) {
                                                                for (DataSnapshot card : s.getChildren()) {
                                                                    for (DataSnapshot active : card.getChildren()) {
                                                                        if (active.getKey().equals("active")) {
                                                                            act = active.getValue().toString();
                                                                            if (act.equals("true")) {
                                                                                int positionIndex = getAdapterPosition();
                                                                                Toast.makeText(context, "Карта " + positionIndex + " заблокирована", Toast.LENGTH_SHORT).show();
                                                                                text_block_card.setText("Карта заблокирована");
                                                                                FirebaseDatabase.getInstance().getReference("User")
                                                                                        .child(mAuth.getUid()).child("Card").child(String.valueOf(positionIndex)).child("active").setValue("false");

                                                                                return;
                                                                            }
                                                                        }
                                                                        break;
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
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Не верный пароль!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Проверте ввод!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                    alertDialog.show();

                }
            });
        }
    }
}
