package com.corner.apps.ui.lastmatchliga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.LastMatchAdapter;
import com.corner.apps.Entity.Event;
import com.corner.apps.LigaActivity;
import com.corner.apps.R;
import com.corner.apps.RestDetailLeague;
import com.corner.apps.RestJadwal;
import com.corner.apps.RetrofitGSON;
import com.corner.apps.ui.detailliga.League;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastMatcLigaFragment extends Fragment {
    private ImageView logo;
    private RecyclerView recyclerLast;
    private String id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lastmatchliga, container, false);
        LigaActivity liga = (LigaActivity) getActivity();
        id = liga.getId();
        logo = root.findViewById(R.id.bannerLgLast);
        recyclerLast = root.findViewById(R.id.recyclerlast);
        getdetail();
        getlistLastMatch();

        return root;
    }
    public void getdetail() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Call<RestDetailLeague> getdetailleague = RetrofitGSON.getInstance().api().getListDetailLeague(params);
        getdetailleague.enqueue(new Callback<RestDetailLeague>() {
            @Override
            public void onResponse(Call<RestDetailLeague> call, Response<RestDetailLeague> response) {
                if (response.isSuccessful()) {
                    List<League> listL = response.body().getLeagues();
                    for (League a : listL) {
                        Picasso.with(getContext()).load(a.getStrBanner())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(logo);
                    }
                } else {
                    Toast.makeText(getContext(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestDetailLeague> call, Throwable t) {

            }
        });
    }
    public void getlistLastMatch(){
        HashMap<String,String> params = new HashMap<>();
        params.put("id", id);
        Call<RestJadwal> getlastmact = RetrofitGSON.getInstance().api().getHistoryLeague(params);
        getlastmact.enqueue(new Callback<RestJadwal>() {
            @Override
            public void onResponse(Call<RestJadwal> call, Response<RestJadwal> response) {
                if (response.isSuccessful()){
                    List<Event> listMatch = response.body().getEvents();
                    recyclerLast.setLayoutManager(new LinearLayoutManager(getContext()));
                    LastMatchAdapter adapter = new LastMatchAdapter(listMatch);
                    recyclerLast.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestJadwal> call, Throwable t) {
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
