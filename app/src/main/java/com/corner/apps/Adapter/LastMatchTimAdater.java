package com.corner.apps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Entity.Result;
import com.corner.apps.R;

import java.util.List;

public class LastMatchTimAdater extends RecyclerView.Adapter<LastMatchTimAdater.MainViewHolder> {
    private List<Result> res ;
    public LastMatchTimAdater(List<Result> res){
        this.res = res;
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
        Result hasil = res.get(position);
        holder.homeTeam.setText(hasil.getStrHomeTeam());
        holder.awayTeam.setText(hasil.getStrAwayTeam());
        holder.homeGoal.setText(hasil.getIntHomeScore());
        holder.awayGoal.setText(hasil.getIntAwayScore());
    }

    @Override
    public int getItemCount() {
        return res.size();
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
