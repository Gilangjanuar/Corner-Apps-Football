package com.corner.apps.ui.klasmen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.KlasmenAdapter;
import com.corner.apps.DashboardDrawwerActivity;
import com.corner.apps.Entity.League;
import com.corner.apps.Entity.Table;
import com.corner.apps.R;
import com.corner.apps.RestKlasmen;
import com.corner.apps.RestLeagues;
import com.corner.apps.RetrofitGSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KlasmenFragment extends Fragment {

    private ProgressDialog loading;
    private List<Table> tableKlasmen;
    private List<League> result;
    private KlasmenAdapter AdapterKlasmen;
    private RecyclerView KlasmenRecycler;
    private Spinner LigaKlasmen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_klasmen, container, false);
        KlasmenRecycler = root.findViewById(R.id.recyclerKlasmen);
        LigaKlasmen = root.findViewById(R.id.klasmenLiga);
        isiSpinner();

        LigaKlasmen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading = ProgressDialog.show(getContext(), null, "harap tunggu...", true, false);
                League pilihLiga = (League) parent.getItemAtPosition(position);
                HashMap<String,String> params = new HashMap<>();
                params.put("l", pilihLiga.getIdLeague());
                params.put("s","2019-2020");
                Call<RestKlasmen> getklasmen = RetrofitGSON.getInstance().api().getListKlasmen(params);
                getklasmen.enqueue(new Callback<RestKlasmen>() {
                    @Override
                    public void onResponse(Call<RestKlasmen> call, Response<RestKlasmen> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            tableKlasmen = response.body().getTable();
                            KlasmenRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            AdapterKlasmen = new KlasmenAdapter(tableKlasmen);
                            KlasmenRecycler.setAdapter(AdapterKlasmen);
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }
    public void isiSpinner() {
        loading = ProgressDialog.show(getContext(), null, "harap tunggu...", true, false);

        HashMap<String, String> maps = new HashMap<>();
        Call<RestLeagues> getLeagues = RetrofitGSON.getInstance().api().getListLeagues(maps);
        getLeagues.enqueue(new Callback<RestLeagues>() {
            @Override
            public void onResponse(Call<RestLeagues> call, Response<RestLeagues> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    result = response.body().getLeagues();
                    ArrayList<League> isi = new ArrayList<>();
                    for (int i = 0; i < result.size(); i++) {
                        isi.add(new League(
                                result.get(i).getIdLeague()
                                , result.get(i).getStrLeague()
                                , result.get(i).getStrSport()
                                , result.get(i).getStrLeagueAlternate()));
                    }

                    ArrayAdapter<League> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, isi);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    LigaKlasmen.setAdapter(adapter);
                } else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestLeagues> call, Throwable t) {

            }
        });
    }
}
