package com.corner.apps.ui.beranda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.corner.apps.DashboardDrawwerActivity;
import com.corner.apps.Entity.DetailLeague;
import com.corner.apps.Entity.League;
import com.corner.apps.Adapter.LigaAdapter;
import com.corner.apps.R;
import com.corner.apps.RestDetailLeague;
import com.corner.apps.RestLeagues;
import com.corner.apps.RetrofitGSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaFragment extends Fragment {


    private RecyclerView listLeagues;
    private LigaAdapter ligaAdapter;
    private List<League> result;
    private ArrayList<String> ligaBola, idBola , gambarlogo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        listLeagues = view.findViewById(R.id.leagueRecycler);
        gambarlogo = new ArrayList<>();
        idBola = new ArrayList<>();
        HashMap<String, String> maps = new HashMap<>();
        Call<RestLeagues> getLeague = RetrofitGSON.getInstance().api().getListLeagues(maps);
        getLeague.enqueue(new Callback<RestLeagues>() {
            @Override
            public void onResponse(Call<RestLeagues> call, Response<RestLeagues> response) {
                if (response.isSuccessful()) {
                    result = response.body().getLeagues();
                    listLeagues.setLayoutManager(new LinearLayoutManager(getContext()));
                    ligaAdapter = new LigaAdapter(result , getActivity());
                    listLeagues.setAdapter(ligaAdapter);
                    ligaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestLeagues> call, Throwable t) {

            }
        });

        return view;
    }
}


