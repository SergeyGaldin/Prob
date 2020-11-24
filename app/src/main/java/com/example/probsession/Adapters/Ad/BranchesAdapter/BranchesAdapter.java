package com.example.probsession.Adapters.Ad.BranchesAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.probsession.R;

import java.util.List;

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.BranchesAdapterVH> {
    LayoutInflater layoutInflater;
    List<Branch> branchArrayList;

    public BranchesAdapter(Context context, List<Branch> branchArrayList) {
        this.layoutInflater = layoutInflater.from(context);
        this.branchArrayList = branchArrayList;
    }

    @NonNull
    @Override
    public BranchesAdapter.BranchesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BranchesAdapterVH(layoutInflater.inflate(R.layout.branches_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BranchesAdapter.BranchesAdapterVH holder, int position) {
        Branch branch = branchArrayList.get(position);

        holder.address.setText(branch.getAddress());
        holder.place.setText(branch.getPlace());
        holder.work.setText(branch.getWork());
        holder.clock.setText(branch.getClock());

        if (holder.work.getText().toString().equals("Не работает")) {
            holder.work.setTextColor(Color.RED);
        } else {
            holder.work.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return branchArrayList.size();
    }

    public class BranchesAdapterVH extends RecyclerView.ViewHolder {
        TextView address, place, work, clock;

        public BranchesAdapterVH(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            place = itemView.findViewById(R.id.place);
            work = itemView.findViewById(R.id.work);
            clock = itemView.findViewById(R.id.clock);
        }
    }
}
