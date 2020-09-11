package com.corner.apps.ui.jadwal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.JadwalAdapter;
import com.corner.apps.DashboardDrawwerActivity;
import com.corner.apps.Entity.Event;
import com.corner.apps.Entity.League;
import com.corner.apps.R;
import com.corner.apps.RestJadwal;
import com.corner.apps.RestLeagues;
import com.corner.apps.RetrofitGSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalFragment extends Fragment {

    private RecyclerView listJadwal;
    private JadwalAdapter jadwalAdapter;
    private List<League> result;
    private Spinner listLiga;
    private  ProgressDialog loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jadwal, container, false);
        listLiga = root.findViewById(R.id.spinnerLiga);
        listJadwal = root.findViewById(R.id.recyclerJadwal);
        isiSpinner();
        listLiga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading = ProgressDialog.show(getContext(), null, "harap tunggu...", true, false);
                League pilihLiga = (League) parent.getItemAtPosition(position);
                HashMap<String,String> params = new HashMap<>();
                params.put("id", pilihLiga.getIdLeague());
                Call<RestJadwal> getjdwl = RetrofitGSON.getInstance().api().getListJadwal(params);
                getjdwl.enqueue(new Callback<RestJadwal>() {
                    @Override
                    public void onResponse(Call<RestJadwal> call, Response<RestJadwal> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            List<Event> listMatch = response.body().getEvents();
                            listJadwal.setLayoutManager(new LinearLayoutManager(getContext()));
                            jadwalAdapter = new JadwalAdapter(listMatch);
                            listJadwal.setAdapter(jadwalAdapter);
                            jadwalAdapter.notifyDataSetChanged();
                        }
                        else {
                            loading.dismiss();
                            Toast.makeText(getContext(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestJadwal> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    public void isiSpinner(){
        loading = ProgressDialog.show(getContext(), null, "harap tunggu...", true, false);

        HashMap<String, String> maps = new HashMap<>();
        Call<RestLeagues> getLeagues = RetrofitGSON.getInstance().api().getListLeagues(maps);
        getLeagues.enqueue(new Callback<RestLeagues>() {
            @Override
            public void onResponse(Call<RestLeagues> call, Response<RestLeagues> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    result = response.body().getLeagues();
                    ArrayList<League> isi = new ArrayList<>();
                    for (int i = 0 ; i < result.size() ; i++){
                        isi.add(new League(
                                result.get(i).getIdLeague()
                                ,result.get(i).getStrLeague()
                                ,result.get(i).getStrSport()
                                ,result.get(i).getStrLeagueAlternate()));
                    }

                    ArrayAdapter<League> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, isi);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listLiga.setAdapter(adapter);
                }
                else {
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
