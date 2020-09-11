package com.corner.apps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Entity.Table;
import com.corner.apps.R;

import java.util.List;

public class KlasmenAdapter extends RecyclerView.Adapter<KlasmenAdapter.MainViewHolder>{
    private List<Table> klasmen ;

    public KlasmenAdapter(List<Table> klasmen) {
        this.klasmen = klasmen;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_klasmen, parent, false);
        MainViewHolder show = new MainViewHolder(template);
        return show;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Table fixKlasmen = klasmen.get(position);
        holder.txtNo.setText(String.valueOf(klasmen.indexOf(fixKlasmen)+1));
        holder.txtNama.setText(fixKlasmen.getName());
        holder.txtPoint.setText(fixKlasmen.getTotal().toString());
        holder.txtMain.setText(fixKlasmen.getPlayed().toString());
        holder.txtWin.setText(fixKlasmen.getWin().toString());
        holder.txtDraw.setText(fixKlasmen.getDraw().toString());
        holder.txtLose.setText(fixKlasmen.getLoss().toString());
        holder.txtGoal.setText(fixKlasmen.getGoalsfor()+" - "+fixKlasmen.getGoalsagainst());
    }

    @Override
    public int getItemCount() {
        return klasmen.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView txtNo, txtNama , txtPoint , txtMain , txtWin , txtDraw ,txtLose , txtGoal;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.no);
            txtNama = itemView.findViewById(R.id.nama);
            txtPoint = itemView.findViewById(R.id.point);
            txtMain = itemView.findViewById(R.id.main);
            txtWin = itemView.findViewById(R.id.win);
            txtDraw = itemView.findViewById(R.id.draw);
            txtLose = itemView.findViewById(R.id.lose);
            txtGoal = itemView.findViewById(R.id.goal);
        }
    }
}
