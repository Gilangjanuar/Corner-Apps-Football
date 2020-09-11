package com.corner.apps.ui.detailliga;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.KlasmenAdapter;
import com.corner.apps.Entity.Table;
import com.corner.apps.LigaActivity;
import com.corner.apps.R;
import com.corner.apps.RestDetailLeague;
import com.corner.apps.RestKlasmen;
import com.corner.apps.RetrofitGSON;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLigaFragment extends Fragment {
    private String id;
    private List<Table> klasmen;
    private ImageView banner;
    private RecyclerView recyclerklasmen;
    private TextView deskripsi;
    private ProgressDialog loading;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LigaActivity liga = (LigaActivity) getActivity();
        id = liga.getId();
        View root = inflater.inflate(R.layout.fragment_detailliga, container, false);
        banner = root.findViewById(R.id.bannerLiga);
        deskripsi = root.findViewById(R.id.deskripsiLiga);
        deskripsi.setMovementMethod(new ScrollingMovementMethod());
        recyclerklasmen = root.findViewById(R.id.recyclerKlasmenDetail);
        getdetail();
        getKlasmen();

        return root;
    }


    public void getdetail(){
        HashMap<String , String> params = new HashMap<>();
        params.put("id",id);
        Call<RestDetailLeague> getdetailleague = RetrofitGSON.getInstance().api().getListDetailLeague(params);
        loading = ProgressDialog.show(getContext(), null, "harap tunggu...", true, false);
        getdetailleague.enqueue(new Callback<RestDetailLeague>() {
            @Override
            public void onResponse(Call<RestDetailLeague> call, Response<RestDetailLeague> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    List<League> listL = response.body().getLeagues();
                    for(League a : listL) {
                        Picasso.with(getContext()).load(a.getStrBanner())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(banner);
                        deskripsi.setText(a.getStrDescriptionEN());
                    }
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestDetailLeague> call, Throwable t) {

            }
        });
    }

    public void getKlasmen(){
        HashMap<String,String> params = new HashMap<>();
        params.put("l", id);
        params.put("s","2019-2020");
        Call<RestKlasmen> getklasmen = RetrofitGSON.getInstance().api().getListKlasmen(params);
        getklasmen.enqueue(new Callback<RestKlasmen>() {
            @Override
            public void onResponse(Call<RestKlasmen> call, Response<RestKlasmen> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    List<Table> tableKlasmen = response.body().getTable();
                    recyclerklasmen.setLayoutManager(new LinearLayoutManager(getContext()));
                    KlasmenAdapter AdapterKlasmen = new KlasmenAdapter(tableKlasmen);
                    recyclerklasmen.setAdapter(AdapterKlasmen);
                    AdapterKlasmen.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RestKlasmen> call, Throwable t) {

            }
        });
    }

}
