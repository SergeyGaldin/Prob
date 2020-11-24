package com.example.probsession.Adapters.Ad.HistoryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probsession.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterVH> {
    LayoutInflater inflater;
    List<History> historyList;

    public HistoryAdapter(Context context, List<History> historyList) {
        this.inflater = inflater.from(context);
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapterVH(inflater.inflate(R.layout.history_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterVH holder, int position) {
        History history = historyList.get(position);
        holder.date.setText(history.getDate());
        holder.time.setText(history.getTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryAdapterVH extends RecyclerView.ViewHolder {
        TextView date, time;
        public HistoryAdapterVH(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
