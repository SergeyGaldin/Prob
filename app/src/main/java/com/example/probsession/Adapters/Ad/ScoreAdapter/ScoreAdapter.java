package com.example.probsession.Adapters.Ad.ScoreAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probsession.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreAdapterVH> {
    LayoutInflater inflater;
    List<Score> scoreList;

    public ScoreAdapter(Context context, List<Score> scoreList) {
        this.inflater = inflater.from(context);
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreAdapterVH(inflater.inflate(R.layout.score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapterVH holder, int position) {
        Score score = scoreList.get(position);
        StringBuilder builder = new StringBuilder(score.getNum());
        builder.replace(0, 4, "****");
        holder.num.setText(builder);
        holder.money.setText(score.getMoney() + " рублей");
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public class ScoreAdapterVH extends RecyclerView.ViewHolder {
        TextView num, money;
        public ScoreAdapterVH(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num_card);
            money = itemView.findViewById(R.id.sum_card);
        }
    }
}
