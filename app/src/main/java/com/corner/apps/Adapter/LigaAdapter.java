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

import com.corner.apps.DashboardDrawwerActivity;
import com.corner.apps.Entity.League;
import com.corner.apps.LigaActivity;
import com.corner.apps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.MainViewHolder> {
    private List<League> listLeagues;
    private Context mContext;
    private ArrayList<String> getlogo;

    public LigaAdapter(List<League> listLeagues , Context mContext) {
        this.listLeagues = listLeagues;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_league, parent, false);
        MainViewHolder show = new MainViewHolder(template);
        return show;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {

        League ligalist = listLeagues.get(position);
        final String txtidLiga = ligalist.getIdLeague();
        holder.txtnamaLiga.setText(ligalist.getStrLeague());
//        Picasso.with(mContext).load(getlogo.get(position))
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.imglogoLiga);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(mContext, LigaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",ligalist.getIdLeague());
                a.putExtras(bundle);
                mContext.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLeagues.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView txtnamaLiga;
        ImageView imglogoLiga;
        CardView card;

        public MainViewHolder(View template) {
            super(template);
            txtnamaLiga = template.findViewById(R.id.namaLiga);
            imglogoLiga = template.findViewById(R.id.logoLiga);
            card = template.findViewById(R.id.cardLiga);
        }
    }
}
