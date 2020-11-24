package com.example.probsession.Adapters.Ad.CardAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probsession.CardActivity;
import com.example.probsession.HomeActivity;
import com.example.probsession.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardAdapterVH> {
    LayoutInflater inflater;
    List<Card> cardList;
    Context context;

    public CardAdapter(Context context, List<Card> cardList) {
        this.inflater = inflater.from(context);
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardAdapter.CardAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardAdapterVH(inflater.inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardAdapterVH holder, int position) {
        Card card = cardList.get(position);
        StringBuilder builder = new StringBuilder(card.getNum_card());
        builder.replace(4, 7, "***");
        holder.num_card.setText(builder);
        holder.money_card.setText(card.getMoney_card() + " рублей");

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardAdapterVH extends RecyclerView.ViewHolder {
        TextView num_card, money_card;

        public CardAdapterVH(@NonNull View itemView) {
            super(itemView);
            num_card = itemView.findViewById(R.id.num_card);
            money_card = itemView.findViewById(R.id.sum_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();
                    Toast.makeText(context, "Elements " + positionIndex, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, CardActivity.class));
                }
            });
        }

    }
}
