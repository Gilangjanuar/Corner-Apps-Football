package com.corner.apps.ui.nextmatchliga;

import android.app.ProgressDialog;
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

import com.corner.apps.Adapter.JadwalAdapter;
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

public class NextMatchLigaFragment extends Fragment {
    private String id;
    private ImageView banner;
    private RecyclerView recyclerNext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LigaActivity liga = (LigaActivity) getActivity();
        id = liga.getId();
        View root = inflater.inflate(R.layout.fragment_nextmatcliga, container, false);
        banner = root.findViewById(R.id.bannerLg);
        recyclerNext = root.findViewById(R.id.recyclernext);
        getdetail();
        getlistNextMatch();
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
                                .into(banner);
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
    public void getlistNextMatch(){
        HashMap<String,String> params = new HashMap<>();
        params.put("id", id);
        Call<RestJadwal> getjdwl = RetrofitGSON.getInstance().api().getListJadwal(params);
        getjdwl.enqueue(new Callback<RestJadwal>() {
            @Override
            public void onResponse(Call<RestJadwal> call, Response<RestJadwal> response) {
                if (response.isSuccessful()){
                    List<Event> listMatch = response.body().getEvents();
                    recyclerNext.setLayoutManager(new LinearLayoutManager(getContext()));
                    JadwalAdapter adapter = new JadwalAdapter(listMatch);
                    recyclerNext.setAdapter(adapter);
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
