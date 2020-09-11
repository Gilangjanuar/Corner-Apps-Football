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

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MainViewHolder> {
    private List<Event> listEvent;

    public JadwalAdapter(List<Event> listEvent){
        this.listEvent = listEvent;
    }

    @NonNull
    @Override
    public JadwalAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_jadwal, parent, false);
        MainViewHolder show = new MainViewHolder(template);
        return show;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Event eventList = listEvent.get(position);
        holder.txtHome.setText(eventList.getStrHomeTeam());
        holder.txtAway.setText(eventList.getStrAwayTeam());
        holder.txtTime.setText(eventList.getStrTime());
        holder.txtDate.setText(eventList.getStrDate());
    }


    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView txtHome , txtAway , txtTime , txtDate;
        public MainViewHolder(View template) {
            super(template);
            txtHome = template.findViewById(R.id.home);
            txtAway = template.findViewById(R.id.away);
            txtTime = template.findViewById(R.id.time);
            txtDate = template.findViewById(R.id.date);
        }
    }
}
