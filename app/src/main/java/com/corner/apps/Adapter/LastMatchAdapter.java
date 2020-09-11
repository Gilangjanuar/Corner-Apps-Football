package com.corner.apps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Entity.Event;
import com.corner.apps.R;

import java.util.List;

public class LastMatchAdapter extends RecyclerView.Adapter<LastMatchAdapter.MainViewHolder> {
    private List<Event> listHistory;
    public LastMatchAdapter(List<Event> listHistory ){
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_lastmatch, parent, false);
        MainViewHolder show = new MainViewHolder(template);
        return show;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Event hasil = listHistory.get(position);
        holder.homeTeam.setText(hasil.getStrHomeTeam());
        holder.awayTeam.setText(hasil.getStrAwayTeam());
        holder.homeGoal.setText(hasil.getIntHomeScore().toString());
        holder.awayGoal.setText(hasil.getIntAwayScore().toString());
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeam , awayTeam , homeGoal , awayGoal , homePlayer , awayPlayer;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.hometeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            homeGoal = itemView.findViewById(R.id.skorhome);
            awayGoal = itemView.findViewById(R.id.skoraway);
        }
    }
}
