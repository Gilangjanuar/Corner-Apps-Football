package com.corner.apps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Entity.Team;
import com.corner.apps.R;
import com.corner.apps.TimActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TimAdapter extends RecyclerView.Adapter<TimAdapter.MainViewHolder> {

    private List<Team> listTeam;
    private Context mContext;

    public TimAdapter(List<Team> listTeam , Context mContext){
        this.listTeam = listTeam;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_tim, parent, false);
        MainViewHolder show = new MainViewHolder(template);
        return show;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Team timList = listTeam.get(position);
        holder.namaTim.setText(timList.getStrTeam());
        Picasso.with(mContext).load(timList.getStrTeamBadge())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.logoTim);
        holder.cardTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(mContext, TimActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",timList.getIdTeam());
                a.putExtras(bundle);
                mContext.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardTim;
        TextView namaTim;
        ImageView logoTim;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTim = itemView.findViewById(R.id.cardTim);
            namaTim = itemView.findViewById(R.id.namaTim);
            logoTim = itemView.findViewById(R.id.logoTim);
        }
    }
}
